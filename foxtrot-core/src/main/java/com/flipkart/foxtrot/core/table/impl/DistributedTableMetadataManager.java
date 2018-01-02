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
package com.flipkart.foxtrot.core.table.impl;

import com.carrotsearch.hppc.cursors.ObjectCursor;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.foxtrot.common.FieldMetadata;
import com.flipkart.foxtrot.common.FieldType;
import com.flipkart.foxtrot.common.Table;
import com.flipkart.foxtrot.common.TableFieldMapping;
import com.flipkart.foxtrot.common.estimation.CardinalityEstimationData;
import com.flipkart.foxtrot.common.estimation.FixedEstimationData;
import com.flipkart.foxtrot.common.estimation.PercentileEstimationData;
import com.flipkart.foxtrot.common.util.CollectionUtils;
import com.flipkart.foxtrot.core.exception.FoxtrotException;
import com.flipkart.foxtrot.core.exception.FoxtrotExceptions;
import com.flipkart.foxtrot.core.parsers.ElasticsearchMappingParser;
import com.flipkart.foxtrot.core.querystore.actions.Utils;
import com.flipkart.foxtrot.core.querystore.impl.ElasticsearchConnection;
import com.flipkart.foxtrot.core.querystore.impl.ElasticsearchUtils;
import com.flipkart.foxtrot.core.querystore.impl.HazelcastConnection;
import com.flipkart.foxtrot.core.table.TableMetadataManager;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.hazelcast.config.InMemoryFormat;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.config.NearCacheConfig;
import com.hazelcast.core.IMap;
import lombok.SneakyThrows;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.search.MultiSearchRequestBuilder;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.elasticsearch.search.aggregations.metrics.percentiles.Percentiles;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * User: Santanu Sinha (santanu.sinha@flipkart.com)
 * Date: 15/03/14
 * Time: 10:11 PM
 */

public class DistributedTableMetadataManager implements TableMetadataManager {
    private static final Logger logger = LoggerFactory.getLogger(DistributedTableMetadataManager.class);
    private static final String DATA_MAP = "tablemetadatamap";
    private static final String FIELD_MAP = "tablefieldmap";
    private final HazelcastConnection hazelcastConnection;
    private final ElasticsearchConnection elasticsearchConnection;
    private final ObjectMapper mapper;
    private IMap<String, Table> tableDataStore;
    private IMap<String, TableFieldMapping> fieldDataCache;

    public DistributedTableMetadataManager(HazelcastConnection hazelcastConnection,
                                           ElasticsearchConnection elasticsearchConnection,
                                           ObjectMapper mapper) {
        this.hazelcastConnection = hazelcastConnection;
        this.elasticsearchConnection = elasticsearchConnection;
        this.mapper = mapper;

        hazelcastConnection.getHazelcastConfig().getMapConfigs().put(DATA_MAP, tableMapConfig());
        hazelcastConnection.getHazelcastConfig().getMapConfigs().put(FIELD_MAP, fieldMetaMapConfig());
    }


    private MapConfig tableMapConfig() {
        MapConfig mapConfig = new MapConfig();
        mapConfig.setReadBackupData(true);
        mapConfig.setInMemoryFormat(InMemoryFormat.BINARY);
        mapConfig.setTimeToLiveSeconds(300);
        mapConfig.setBackupCount(0);

        MapStoreConfig mapStoreConfig = new MapStoreConfig();
        mapStoreConfig.setFactoryImplementation(TableMapStore.factory(elasticsearchConnection));
        mapStoreConfig.setEnabled(true);
        mapStoreConfig.setInitialLoadMode(MapStoreConfig.InitialLoadMode.EAGER);
        mapConfig.setMapStoreConfig(mapStoreConfig);

        NearCacheConfig nearCacheConfig = new NearCacheConfig();
        nearCacheConfig.setTimeToLiveSeconds(300);
        nearCacheConfig.setInvalidateOnChange(true);
        mapConfig.setNearCacheConfig(nearCacheConfig);
        return mapConfig;
    }

    private MapConfig fieldMetaMapConfig() {
        MapConfig mapConfig = new MapConfig();
        mapConfig.setReadBackupData(true);
        mapConfig.setInMemoryFormat(InMemoryFormat.BINARY);
        mapConfig.setTimeToLiveSeconds(300);
        mapConfig.setBackupCount(0);

        NearCacheConfig nearCacheConfig = new NearCacheConfig();
        nearCacheConfig.setTimeToLiveSeconds(300);
        nearCacheConfig.setInvalidateOnChange(true);
        mapConfig.setNearCacheConfig(nearCacheConfig);

        return mapConfig;
    }

    private static class FieldMetadataComparator implements Comparator<FieldMetadata>, Serializable {

        private static final long serialVersionUID = 8557746595191991528L;

        @Override
        public int compare(FieldMetadata o1, FieldMetadata o2) {
            if (o1 == null && o2 == null) {
                return 0;
            } else if (o1 == null) {
                return -1;
            } else if (o2 == null) {
                return 1;
            } else {
                return o1.getField().compareTo(o2.getField());
            }
        }
    }


    @Override
    public void save(Table table) throws FoxtrotException {
        logger.info(String.format("Saving Table : %s", table));
        tableDataStore.put(table.getName(), table);
        tableDataStore.flush();
    }

    @Override
    public Table get(String tableName) throws FoxtrotException {
        logger.debug(String.format("Getting Table : %s", tableName));
        if (tableDataStore.containsKey(tableName)) {
            return tableDataStore.get(tableName);
        }
        return null;
    }

    @Override
    @SneakyThrows
    public List<Table> get() throws FoxtrotException {
        if (0 == tableDataStore.size()) { //HACK::Check https://github.com/hazelcast/hazelcast/issues/1404
            return Collections.emptyList();
        }
        ArrayList<Table> tables = Lists.newArrayList(tableDataStore.values());
        tables.sort(Comparator.comparing(table -> table.getName().toLowerCase()));
        return tables;
    }

    @Override
    @Timed
    public TableFieldMapping getFieldMappings(String originalTableName) throws FoxtrotException {
        final String table = ElasticsearchUtils.getValidTableName(originalTableName);

        if (!tableDataStore.containsKey(table)) {
            throw FoxtrotExceptions.createBadRequestException(table,
                    String.format("unknown_table table:%s", table));
        }
        if (fieldDataCache.containsKey(table)) {
            return fieldDataCache.get(table);
        }
        ElasticsearchMappingParser mappingParser = new ElasticsearchMappingParser(mapper);
        final String indices = ElasticsearchUtils.getIndices(table);
        logger.info("Selected indices: {}", indices);
        GetMappingsResponse mappingsResponse = elasticsearchConnection.getClient().admin()
                .indices().prepareGetMappings(indices).execute().actionGet();

        Set<String> indicesName = Sets.newHashSet();
        for (ObjectCursor<String> index : mappingsResponse.getMappings().keys()) {
            indicesName.add(index.value);
        }
        List<FieldMetadata> fieldMetadata = indicesName.stream()
                .filter(x -> !CollectionUtils.isNullOrEmpty(x))
                .sorted((lhs, rhs) -> {
                    Date lhsDate = ElasticsearchUtils.parseIndexDate(lhs, table).toDate();
                    Date rhsDate = ElasticsearchUtils.parseIndexDate(rhs, table).toDate();
                    return 0 - lhsDate.compareTo(rhsDate);
                })
                .map(index -> mappingsResponse.mappings()
                        .get(index)
                        .get(ElasticsearchUtils.DOCUMENT_TYPE_NAME))
                .flatMap(mappingData -> {
                    try {
                        return mappingParser.getFieldMappings(mappingData).stream();
                    } catch (IOException e) {
                        logger.error("Could not read mapping from " + mappingData, e);
                        return Stream.empty();
                    }
                })
                .collect(Collectors.toList());
        final TreeSet<FieldMetadata> fieldMetadataTreeSet = new TreeSet<>(new FieldMetadataComparator());
        fieldMetadataTreeSet.addAll(fieldMetadata);
        TableFieldMapping tableFieldMapping = new TableFieldMapping(table, fieldMetadataTreeSet);
        estimateCardinality(table, tableFieldMapping.getMappings(), DateTime.now().minusDays(1).toDate().getTime());
        fieldDataCache.put(table, tableFieldMapping);
        return tableFieldMapping;
    }

    @Override
    public void updateEstimationData(final String table, long timestamp) throws FoxtrotException {
        if (!tableDataStore.containsKey(table)) {
            throw FoxtrotExceptions.createBadRequestException(table,
                    String.format("unknown_table table:%s", table));
        }
        final TableFieldMapping tableFieldMapping = getFieldMappings(table);
        estimateCardinality(table, tableFieldMapping.getMappings(), timestamp);
        fieldDataCache.put(table, tableFieldMapping);
    }

    private void estimateCardinality(final String table, final Collection<FieldMetadata> fields, long time) throws FoxtrotException {
        if (CollectionUtils.isNullOrEmpty(fields)) {
            logger.warn("No fields.. Nothing to query");
            return;
        }
        Map<String, FieldMetadata> fieldMap = fields
                .stream()
                .collect(Collectors.toMap(FieldMetadata::getField, fieldMetadata -> fieldMetadata, (lhs, rhs) -> lhs));

        final String index = ElasticsearchUtils.getCurrentIndex(ElasticsearchUtils.getValidTableName(table), time);
        final Client client = elasticsearchConnection.getClient();

        MultiSearchRequestBuilder multiQuery = client.prepareMultiSearch();

        fields.forEach(fieldMetadata -> {
            String field = fieldMetadata.getField();
            SearchRequestBuilder query = client.prepareSearch(index)
                    .setIndicesOptions(Utils.indicesOptions())
                    .setQuery(QueryBuilders.existsQuery(field))
                    .setSize(0);
            switch (fieldMetadata.getType()) {
                case STRING: {
                    logger.info("table:{} field:{} type:{} aggregationType:{}", table, field, fieldMetadata.getType(), "cardinality");
                    query.addAggregation(AggregationBuilders.cardinality(field)
                            .field(field)
                            .precisionThreshold(100));
                    break;
                }
                case INTEGER:
                case LONG:
                case FLOAT:
                case DOUBLE: {
                    logger.info("table:{} field:{} type:{} aggregationType:{}", table, field, fieldMetadata.getType(), "percentile");
                    query.addAggregation(AggregationBuilders.percentiles(field)
                            .field(field)
                            .percentiles(10, 20, 30, 40, 50, 60, 70, 80, 90, 100));
                    break;
                }
                case BOOLEAN:
                case DATE:
                case OBJECT:
            }
            multiQuery.add(query);
        });

        MultiSearchResponse multiResponse = multiQuery
                .execute()
                .actionGet();

        for (MultiSearchResponse.Item item : multiResponse.getResponses()) {
            if (item.isFailure()) {
                logger.info("FailureInDeducingCardinality table:{} failureMessage:{}", table,
                        item.getFailureMessage());
                continue;
            }
            SearchResponse response = item.getResponse();
            final long hits = response.getHits().totalHits();
            Aggregations aggregations = response.getAggregations();
            if (null == aggregations) {
                continue;
            }
            Map<String, Aggregation> output = aggregations.asMap();
            output.forEach((key, value) -> {
                FieldMetadata fieldMetadata = fieldMap.get(key);
                switch (fieldMetadata.getType()) {
                    case STRING: {
                        Cardinality cardinality = (Cardinality) value;
                        logger.info("table:{} field:{} type:{} aggregationType:{} value:{}",
                                table, key, fieldMetadata.getType(), "cardinality", cardinality.getValue());
                        fieldMetadata.setEstimationData(CardinalityEstimationData.builder()
                                .cardinality(cardinality.getValue())
                                .count(hits)
                                .build());
                        break;
                    }
                    case INTEGER:
                    case LONG:
                    case FLOAT:
                    case DOUBLE: {
                        double values[] = new double[10];
                        Percentiles percentiles = (Percentiles) value;
                        for (int i = 10; i <= 100; i += 10)
                            values[(i / 10) - 1] = percentiles.percentile(i);
                        logger.info("table:{} field:{} type:{} aggregationType:{} value:{}",
                                table, key, fieldMetadata.getType(), "percentile", values);
                        fieldMetadata.setEstimationData(PercentileEstimationData.builder()
                                .values(values)
                                .count(hits)
                                .build());
                        break;
                    }
                    case BOOLEAN:
                    case DATE:
                    case OBJECT:
                }
            });
        }
        fields.stream()
                .filter(fieldMetadata -> fieldMetadata.getType().equals(FieldType.BOOLEAN))
                .forEach(fieldMetadata -> fieldMetadata.setEstimationData(FixedEstimationData.builder()
                        .count(2)
                        .build()));
    }

    @Override
    public boolean exists(String tableName) throws FoxtrotException {
        return tableDataStore.containsKey(tableName);
    }

    @Override
    public void delete(String tableName) throws FoxtrotException {
        logger.info(String.format("Deleting Table : %s", tableName));
        if (tableDataStore.containsKey(tableName)) {
            tableDataStore.delete(tableName);
        }
        logger.info(String.format("Deleted Table : %s", tableName));
    }

    @Override
    public void start() throws Exception {
        tableDataStore = hazelcastConnection.getHazelcast().getMap(DATA_MAP);
        fieldDataCache = hazelcastConnection.getHazelcast().getMap(FIELD_MAP);
    }

    @Override
    public void stop() throws Exception {
    }
}
