/**
 * Copyright 2014 Flipkart Internet Pvt. Ltd.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.flipkart.foxtrot.core.querystore.actions;

import com.flipkart.foxtrot.common.ActionResponse;
import com.flipkart.foxtrot.common.FieldMetadata;
import com.flipkart.foxtrot.common.TableFieldMapping;
import com.flipkart.foxtrot.common.estimation.CardinalityEstimationData;
import com.flipkart.foxtrot.common.estimation.EstimationDataVisitor;
import com.flipkart.foxtrot.common.estimation.FixedEstimationData;
import com.flipkart.foxtrot.common.estimation.PercentileEstimationData;
import com.flipkart.foxtrot.common.group.GroupRequest;
import com.flipkart.foxtrot.common.group.GroupResponse;
import com.flipkart.foxtrot.common.query.Filter;
import com.flipkart.foxtrot.common.query.FilterCombinerType;
import com.flipkart.foxtrot.common.query.FilterVisitorAdapter;
import com.flipkart.foxtrot.common.query.general.EqualsFilter;
import com.flipkart.foxtrot.common.query.general.InFilter;
import com.flipkart.foxtrot.common.query.general.NotEqualsFilter;
import com.flipkart.foxtrot.common.query.general.NotInFilter;
import com.flipkart.foxtrot.common.query.numeric.*;
import com.flipkart.foxtrot.common.query.string.ContainsFilter;
import com.flipkart.foxtrot.common.util.CollectionUtils;
import com.flipkart.foxtrot.core.cache.CacheManager;
import com.flipkart.foxtrot.core.common.Action;
import com.flipkart.foxtrot.core.common.PeriodSelector;
import com.flipkart.foxtrot.core.datastore.DataStore;
import com.flipkart.foxtrot.core.exception.FoxtrotException;
import com.flipkart.foxtrot.core.exception.FoxtrotExceptions;
import com.flipkart.foxtrot.core.exception.MalformedQueryException;
import com.flipkart.foxtrot.core.querystore.QueryStore;
import com.flipkart.foxtrot.core.querystore.actions.spi.AnalyticsProvider;
import com.flipkart.foxtrot.core.querystore.impl.ElasticsearchConnection;
import com.flipkart.foxtrot.core.querystore.impl.ElasticsearchUtils;
import com.flipkart.foxtrot.core.querystore.query.ElasticSearchQueryGenerator;
import com.flipkart.foxtrot.core.table.TableMetadataManager;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.joda.time.Interval;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * User: Santanu Sinha (santanu.sinha@flipkart.com)
 * Date: 27/03/14
 * Time: 7:16 PM
 */
@AnalyticsProvider(opcode = "group", request = GroupRequest.class, response = GroupResponse.class, cacheable = true)
@Slf4j
public class GroupAction extends Action<GroupRequest> {

    private static final long MAX_CARDINALITY = 5000;
    private static final long MIN_ESTIMATION_THRESHOLD = 1000;

    public GroupAction(GroupRequest parameter,
                       TableMetadataManager tableMetadataManager,
                       DataStore dataStore,
                       QueryStore queryStore,
                       ElasticsearchConnection connection,
                       String cacheToken,
                       CacheManager cacheManager) {
        super(parameter, tableMetadataManager, dataStore, queryStore, connection, cacheToken, cacheManager);
    }

    @Override
    protected void preprocess() {
        getParameter().setTable(ElasticsearchUtils.getValidTableName(getParameter().getTable()));
    }

    @Override
    public String getMetricKey() {
        return getParameter().getTable();
    }

    @Override
    protected String getRequestCacheKey() {
        long filterHashKey = 0L;
        GroupRequest query = getParameter();
        if (null != query.getFilters()) {
            for (Filter filter : query.getFilters()) {
                filterHashKey += 31 * filter.hashCode();
            }
        }

        if (null != query.getUniqueCountOn()) {
            filterHashKey += 31 * query.getUniqueCountOn().hashCode();
        }

        for (int i = 0; i < query.getNesting().size(); i++) {
            filterHashKey += 31 * query.getNesting().get(i).hashCode() * (i + 1);
        }
        return String.format("%s-%d", query.getTable(), filterHashKey);
    }

    @Override
    public void validateImpl(GroupRequest parameter) throws MalformedQueryException {
        List<String> validationErrors = new ArrayList<>();
        if (CollectionUtils.isNullOrEmpty(parameter.getTable())) {
            validationErrors.add("table name cannot be null or empty");
        }

        if (CollectionUtils.isNullOrEmpty(parameter.getNesting())) {
            validationErrors.add("at least one grouping parameter is required");
        } else {
            validationErrors.addAll(parameter.getNesting().stream()
                    .filter(CollectionUtils::isNullOrEmpty)
                    .map(field -> "grouping parameter cannot have null or empty name")
                    .collect(Collectors.toList()));
        }

        if (parameter.getUniqueCountOn() != null && parameter.getUniqueCountOn().isEmpty()) {
            validationErrors.add("unique field cannot be empty (can be null)");
        }

        if (!CollectionUtils.isNullOrEmpty(validationErrors)) {
            throw FoxtrotExceptions.createMalformedQueryException(parameter, validationErrors);
        }

        // Perform cardinality analysis and see how much this fucks up the cluster
        double probability = 0;
        try {
            TableFieldMapping fieldMappings = getTableMetadataManager().getFieldMappings(parameter.getTable());
            if (null == fieldMappings) {
                fieldMappings = TableFieldMapping.builder()
                        .mappings(Collections.emptySet())
                        .table(parameter.getTable())
                        .build();
            }

            probability = estimateProbability(fieldMappings, parameter);
        } catch (Exception e) {
            log.error("Error running estimation", e);
        }

        if (probability > 0.5) {
            log.warn("Blocked query as it might have screwed up the cluster. Probability: {} Query: {}",
                    probability, parameter);
            throw FoxtrotExceptions.createCardinalityOverflow(parameter, parameter.getNesting().get(0), probability);
        } else {
            log.info("Allowing group by with probability {} for query: {}", probability, parameter);
        }
    }

    @Override
    public ActionResponse execute(GroupRequest parameter) throws FoxtrotException {
        SearchRequestBuilder query;
        try {
            query = getConnection().getClient()
                    .prepareSearch(ElasticsearchUtils.getIndices(parameter.getTable(), parameter))
                    .setIndicesOptions(Utils.indicesOptions());
            AbstractAggregationBuilder aggregation = buildAggregation();
            query.setQuery(new ElasticSearchQueryGenerator(FilterCombinerType.and)
                    .genFilter(parameter.getFilters()))
                    .setSize(0)
                    .addAggregation(aggregation);
        } catch (Exception e) {
            throw FoxtrotExceptions.queryCreationException(parameter, e);
        }
        try {
            SearchResponse response = query.execute().actionGet();
            List<String> fields = parameter.getNesting();
            Aggregations aggregations = response.getAggregations();
            // Check if any aggregation is present or not
            if (aggregations == null) {
                return new GroupResponse(Collections.<String, Object>emptyMap());
            }
            return new GroupResponse(getMap(fields, aggregations));
        } catch (ElasticsearchException e) {
            throw FoxtrotExceptions.createQueryExecutionException(parameter, e);
        }
    }

    private double estimateProbability(TableFieldMapping tableFieldMapping, GroupRequest parameter) throws Exception {
        Set<FieldMetadata> mappings = tableFieldMapping.getMappings();
        Map<String, FieldMetadata> metaMap = mappings.stream()
                .collect(Collectors.toMap(FieldMetadata::getField, mapping -> mapping));

        long estimatedMaxDocCount = extractMaxDocCount(metaMap);
        log.debug("msg:DOC_COUNT_ESTIMATION_COMPLETED maxDocCount:{}", estimatedMaxDocCount);
        long estimatedDocCountBasedOnTime = estimateDocCountBasedOnTime(estimatedMaxDocCount, parameter);
        log.debug("msg:TIME_BASED_DOC_ESTIMATION_COMPLETED maxDocCount:{} docCountAfterTimeFilters:{}",
                estimatedMaxDocCount, estimatedDocCountBasedOnTime);
        long estimatedDocCountAfterFilters = estimateDocCountWithFilters(estimatedDocCountBasedOnTime, metaMap, parameter.getFilters());
        log.debug("msg:ALL_FILTER_ESTIMATION_COMPLETED maxDocCount:{} docCountAfterTimeFilters:{} docCountAfterFilters:{}",
                estimatedMaxDocCount, estimatedDocCountBasedOnTime, estimatedDocCountAfterFilters);
        if (estimatedDocCountAfterFilters < MIN_ESTIMATION_THRESHOLD) {
            log.debug("msg:NESTING_ESTIMATION_SKIPPED estimatedDocCount:{} threshold:{}", estimatedDocCountAfterFilters, MIN_ESTIMATION_THRESHOLD);
            return 0.0;
        }

        long estimatedOutputCardinality = 1;
        for (String field : parameter.getNesting()) {
            FieldMetadata meta = metaMap.get(field);
            if (null == meta || null == meta.getEstimationData()) {
                log.warn("No estimation data found for field {} of table {}", field, parameter.getTable());
                continue;
            }
            long estimatedNestingFieldCardinality = meta.getEstimationData().accept(new EstimationDataVisitor<Long>() {
                @Override
                public Long visit(FixedEstimationData fixedEstimationData) {
                    return fixedEstimationData.getCount();
                }

                @Override
                public Long visit(PercentileEstimationData percentileEstimationData) {
                    return percentileEstimationData.getCount();
                }

                @Override
                public Long visit(CardinalityEstimationData cardinalityEstimationData) {
                    return (cardinalityEstimationData.getCardinality() * estimatedDocCountAfterFilters) / cardinalityEstimationData.getCount();
                }
            });
            log.debug("msg:NESTING_FIELD_ESTIMATION_COMPLETED field:{} currentOverallCardinality:{} estimatedCardinality:{} estimatedOutputCardinality:{}",
                    field, estimatedOutputCardinality, estimatedNestingFieldCardinality, estimatedOutputCardinality * estimatedNestingFieldCardinality);
            estimatedOutputCardinality *= estimatedNestingFieldCardinality;
        }

        log.debug("msg:NESTING_FIELDS_ESTIMATION_COMPLETED maxDocCount:{} docCountAfterTimeFilters:{} docCountAfterFilters:{} estimatedOutputCardinality:{}",
                estimatedMaxDocCount, estimatedDocCountBasedOnTime, estimatedDocCountAfterFilters, estimatedOutputCardinality);
        if (estimatedOutputCardinality > MAX_CARDINALITY) {
            return 1.0;
        } else {
            return 0;
        }
    }

    private long estimateDocCountBasedOnTime(long currentDocCount, GroupRequest parameter) throws Exception {
        Interval queryInterval = new PeriodSelector(parameter.getFilters()).analyze();
        long minutes = queryInterval.toDuration().getStandardMinutes();
        return (currentDocCount * minutes) / 1440;
    }

    private long extractMaxDocCount(Map<String, FieldMetadata> metaMap) {
        return metaMap.values().stream()
                .map(x -> x.getEstimationData() == null ? 0 : x.getEstimationData().getCount())
                .max(Comparator.naturalOrder())
                .orElse((long) 0);
    }

    private long estimateDocCountWithFilters(long currentDocCount,
                                             Map<String, FieldMetadata> metaMap,
                                             List<Filter> filters) throws Exception {
        if (CollectionUtils.isNullOrEmpty(filters)) {
            return currentDocCount;
        }

        double overallFilterMultiplier = 1;
        for (Filter filter : filters) {
            final String filterField = filter.getField();
            FieldMetadata fieldMetadata = metaMap.get(filterField);
            if (null == fieldMetadata || null == fieldMetadata.getEstimationData()) {
                log.warn("No estimation data found for field {} meta {}", filterField, fieldMetadata);
                continue;
            }
            log.debug("msg:FILTER_ESTIMATION_STARTED filter:{} mapping:{}", filter, fieldMetadata);
            double currentFilterMultiplier = fieldMetadata.getEstimationData()
                    .accept(new EstimationDataVisitor<Double>() {
                        @Override
                        @SneakyThrows
                        public Double visit(FixedEstimationData fixedEstimationData) {
                            return filter.accept(new FilterVisitorAdapter<Double>(1.0) {

                                @Override
                                public Double visit(EqualsFilter equalsFilter) throws Exception {
                                    //If there is a match it will be atmost one out of all the values present
                                    return 1.0 / Utils.ensureOne(fixedEstimationData.getCount());
                                }

                                @Override
                                public Double visit(NotEqualsFilter notEqualsFilter) throws Exception {
                                    // Assuming a match, there will be N-1 unmatched values
                                    double numerator = Utils.ensurePositive(fixedEstimationData.getCount() - 1);
                                    return numerator / Utils.ensureOne(fixedEstimationData.getCount());

                                }

                                @Override
                                public Double visit(ContainsFilter stringContainsFilterElement) throws Exception {
                                    // Assuming there is a match to a value.
                                    // Can be more, but we err on the side of optimism.
                                    return (1.0 / Utils.ensureOne(fixedEstimationData.getCount()));

                                }

                                @Override
                                public Double visit(InFilter inFilter) throws Exception {
                                    // Assuming there are M matches, the probability is M/N
                                    return Utils.ensurePositive(inFilter.getValues().size())
                                            / Utils.ensureOne(fixedEstimationData.getCount());
                                }

                                @Override
                                public Double visit(NotInFilter inFilter) throws Exception {
                                    // Assuming there are M matches, then probability will be N - M / N
                                    return Utils.ensurePositive(fixedEstimationData.getCount() - inFilter.getValues().size())
                                            / Utils.ensureOne(fixedEstimationData.getCount());
                                }
                            });
                        }

                        @Override
                        @SneakyThrows
                        public Double visit(PercentileEstimationData percentileEstimationData) {
                            final double[] percentiles = percentileEstimationData.getValues();
                            final long numMatches = percentileEstimationData.getCount();
                            return filter.accept(new FilterVisitorAdapter<Double>(1.0) {
                                @Override
                                public Double visit(BetweenFilter betweenFilter) throws Exception {

                                    //What percentage percentiles are >= above lower bound
                                    int minBound = IntStream.rangeClosed(0, 9)
                                            .filter(i -> betweenFilter.getFrom().doubleValue()
                                                    <= percentiles[i])
                                            .findFirst()
                                            .orElse(0);
                                    // What percentage of values are > upper bound
                                    int maxBound = IntStream.rangeClosed(0, 9)
                                            .filter(i -> betweenFilter.getTo().doubleValue()
                                                    < percentiles[i])
                                            .findFirst()
                                            .orElse(9);

                                    int numBuckets = maxBound - minBound + 1;
                                    final double result = (double) numBuckets / 10.0;
                                    log.debug("Between filter: {} " +
                                                    "percentiles[{}] = {} to percentiles[{}] = {} " +
                                                    "buckets {} multiplier {}",
                                            betweenFilter,
                                            minBound,
                                            percentiles[minBound],
                                            maxBound,
                                            percentiles[maxBound],
                                            numBuckets,
                                            result);
                                    return result;
                                }

                                @Override
                                public Double visit(EqualsFilter equalsFilter) throws Exception {
                                    Long value = (Long) equalsFilter.getValue();
                                    //What percentage percentiles are >= above lower bound
                                    int minBound = IntStream.rangeClosed(0, 9)
                                            .filter(i -> value <= percentiles[i])
                                            .findFirst()
                                            .orElse(0);
                                    // What percentage of values are > upper bound
                                    int maxBound = IntStream.rangeClosed(0, 9)
                                            .filter(i -> value < percentiles[i])
                                            .findFirst()
                                            .orElse(9);
                                    int numBuckets = maxBound - minBound + 1;
                                    final double result = (double) numBuckets / 10.0;
                                    log.debug("Equals filter: {} numMatches: {} multiplier: {}",
                                            equalsFilter, numMatches, result);
                                    return result;
                                }

                                @Override
                                public Double visit(NotEqualsFilter notEqualsFilter) throws Exception {
                                    // There is no match, so all values will be considered
                                    log.debug("Not equals filter: {} multiplier: 1.0", notEqualsFilter);
                                    return 1.0;
                                }

                                @Override
                                public Double visit(GreaterThanFilter greaterThanFilter) throws Exception {
                                    //Percentage of values greater than given value
                                    //Found when we find a percentile value > bound
                                    int minBound = IntStream.rangeClosed(0, 9)
                                            .filter(i ->
                                                    percentiles[i] > greaterThanFilter.getValue().doubleValue())
                                            //Stop when we find a value
                                            .findFirst()
                                            .orElse(0);

                                    //Everything below this percentile do not affect
                                    final double result = (double) (10 - minBound - 1) / 10.0;
                                    log.debug("Greater than filter: {} percentiles[{}] = {} multiplier: {}",
                                            greaterThanFilter,
                                            minBound,
                                            percentiles[minBound],
                                            result);
                                    return result;
                                }

                                @Override
                                public Double visit(GreaterEqualFilter greaterEqualFilter) throws Exception {
                                    //Percentage of values greater than or equal to given value
                                    //Found when we find a percentile value > bound
                                    int minBound = IntStream.rangeClosed(0, 9)
                                            .filter(i ->
                                                    percentiles[i] >= greaterEqualFilter.getValue().doubleValue())
                                            //Stop when we find a value >= bound
                                            .findFirst()
                                            .orElse(0);

                                    //Everything below this do not affect
                                    final double result = (double) (10 - minBound - 1) / 10.0;
                                    log.debug("Greater equals filter: {} percentiles[{}] = {} multiplier: {}",
                                            greaterEqualFilter,
                                            minBound,
                                            percentiles[minBound],
                                            result);
                                    return result;
                                }

                                @Override
                                public Double visit(LessThanFilter lessThanFilter) throws Exception {
                                    //Percentage of values lesser than to bound
                                    //Found when we find a percentile value >= bound
                                    int minBound = 9 - IntStream.rangeClosed(0, 9)
                                            .filter(i ->
                                                    percentiles[9 - i] < lessThanFilter.getValue().doubleValue())
                                            //Stop when we find a value >= bound
                                            .findFirst()
                                            .orElse(0);

                                    //Everything above this do not affect
                                    final double result = ((double) minBound + 1.0) / 10.0;
                                    log.debug("Less than filter: {} percentiles[{}] = {} multiplier: {}",
                                            lessThanFilter,
                                            minBound,
                                            percentiles[minBound],
                                            result);
                                    return result;
                                }

                                @Override
                                public Double visit(LessEqualFilter lessEqualFilter) throws Exception {
                                    //Percentage of values lesser than or equal to bound
                                    //Found when we find a percentile value > bound
                                    int minBound = 9 - IntStream.rangeClosed(0, 9)
                                            .filter(i ->
                                                    percentiles[9 - i] <= lessEqualFilter.getValue().doubleValue())
                                            //Stop when we find a value > bound
                                            .findFirst()
                                            .orElse(0);
                                    //Everything above this do not affect
                                    final double result = ((double) minBound + 1.0) / 10.0;
                                    log.debug("Less equals filter: {} percentiles[{}] = {} multiplier: {}",
                                            lessEqualFilter,
                                            minBound,
                                            percentiles[minBound],
                                            result);
                                    return result;
                                }
                            });
                        }

                        @Override
                        @SneakyThrows
                        public Double visit(CardinalityEstimationData cardinalityEstimationData) {
                            return filter.accept(new FilterVisitorAdapter<Double>(1.0) {

                                @Override
                                public Double visit(EqualsFilter equalsFilter) throws Exception {
                                    //If there is a match it will be atmost one out of all the values present
                                    return 1.0 / Utils.ensureOne(cardinalityEstimationData.getCardinality());
                                }

                                @Override
                                public Double visit(NotEqualsFilter notEqualsFilter) throws Exception {
                                    // Assuming a match, there will be N-1 unmatched values
                                    double numerator = Utils.ensurePositive(cardinalityEstimationData.getCardinality() - 1);
                                    return numerator / Utils.ensureOne(cardinalityEstimationData.getCardinality());

                                }

                                @Override
                                public Double visit(ContainsFilter stringContainsFilterElement) throws Exception {
                                    // Assuming there is a match to a value.
                                    // Can be more, but we err on the side of optimism.
                                    return (1.0 / Utils.ensureOne(cardinalityEstimationData.getCardinality()));

                                }

                                @Override
                                public Double visit(InFilter inFilter) throws Exception {
                                    // Assuming there are M matches, the probability is M/N
                                    return Utils.ensurePositive(inFilter.getValues().size())
                                            / Utils.ensureOne(cardinalityEstimationData.getCardinality());
                                }

                                @Override
                                public Double visit(NotInFilter inFilter) throws Exception {
                                    // Assuming there are M matches, then probability will be N - M / N
                                    return Utils.ensurePositive(
                                            cardinalityEstimationData.getCardinality()
                                                    - inFilter.getValues().size())
                                            / Utils.ensureOne(cardinalityEstimationData.getCardinality());
                                }
                            });
                        }
                    });
            log.debug("msg:FILTER_ESTIMATION_COMPLETED field:{} fieldMultiplier:{} overallOldMultiplier:{} overallNewMultiplier:{}",
                    filterField, currentFilterMultiplier, overallFilterMultiplier, overallFilterMultiplier * currentFilterMultiplier);
            overallFilterMultiplier *= currentFilterMultiplier;
        }
        return (long) (currentDocCount * overallFilterMultiplier);
    }

    private AbstractAggregationBuilder buildAggregation() {
        TermsBuilder rootBuilder = null;
        TermsBuilder termsBuilder = null;
        for (String field : getParameter().getNesting()) {
            if (null == termsBuilder) {
                termsBuilder = AggregationBuilders.terms(Utils.sanitizeFieldForAggregation(field)).field(field);
            } else {
                TermsBuilder tempBuilder = AggregationBuilders.terms(Utils.sanitizeFieldForAggregation(field)).field(field);
                termsBuilder.subAggregation(tempBuilder);
                termsBuilder = tempBuilder;
            }
            termsBuilder.size(0);
            if (null == rootBuilder) {
                rootBuilder = termsBuilder;
            }
        }

        if (!CollectionUtils.isNullOrEmpty(getParameter().getUniqueCountOn())) {
            assert termsBuilder != null;
            termsBuilder.subAggregation(Utils.buildCardinalityAggregation(getParameter().getUniqueCountOn()));
        }

        return rootBuilder;
    }

    private Map<String, Object> getMap(List<String> fields, Aggregations aggregations) {
        final String field = fields.get(0);
        final List<String> remainingFields = (fields.size() > 1) ? fields.subList(1, fields.size())
                : new ArrayList<>();
        Terms terms = aggregations.get(Utils.sanitizeFieldForAggregation(field));
        Map<String, Object> levelCount = Maps.newHashMap();
        for (Terms.Bucket bucket : terms.getBuckets()) {
            if (fields.size() == 1) {
                if (!CollectionUtils.isNullOrEmpty(getParameter().getUniqueCountOn())) {
                    String key = Utils.sanitizeFieldForAggregation(getParameter().getUniqueCountOn());
                    Cardinality cardinality = bucket.getAggregations().get(key);
                    levelCount.put(String.valueOf(bucket.getKey()), cardinality.getValue());
                } else {
                    levelCount.put(String.valueOf(bucket.getKey()), bucket.getDocCount());
                }
            } else {
                levelCount.put(String.valueOf(bucket.getKey()), getMap(remainingFields, bucket.getAggregations()));
            }
        }
        return levelCount;

    }

}
