/*
 * Licensed to Metamarkets Group Inc. (Metamarkets) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Metamarkets licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.druid.query.groupby.epinephelinae;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import io.druid.collections.ResourceHolder;
import io.druid.common.guava.SettableSupplier;
import io.druid.data.input.Row;
import io.druid.java.util.common.Pair;
import io.druid.java.util.common.StringUtils;
import io.druid.java.util.common.guava.Accumulator;
import io.druid.java.util.common.guava.BaseSequence;
import io.druid.java.util.common.guava.CloseQuietly;
import io.druid.java.util.common.guava.FilteredSequence;
import io.druid.java.util.common.guava.Sequence;
import io.druid.query.Query;
import io.druid.query.ResourceLimitExceededException;
import io.druid.query.aggregation.AggregatorFactory;
import io.druid.query.dimension.DimensionSpec;
import io.druid.query.extraction.ExtractionFn;
import io.druid.query.filter.Filter;
import io.druid.query.filter.ValueMatcher;
import io.druid.query.groupby.GroupByQuery;
import io.druid.query.groupby.GroupByQueryConfig;
import io.druid.query.groupby.RowBasedColumnSelectorFactory;
import io.druid.query.groupby.epinephelinae.RowBasedGrouperHelper.RowBasedKey;
import io.druid.query.groupby.resource.GroupByQueryResource;
import io.druid.segment.DimensionSelector;
import io.druid.segment.column.ValueType;
import io.druid.segment.filter.BooleanValueMatcher;
import io.druid.segment.filter.Filters;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.io.Closeable;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GroupByRowProcessor
{
  public static Sequence<Row> process(
      final Query queryParam,
      final Sequence<Row> rows,
      final Map<String, ValueType> rowSignature,
      final GroupByQueryConfig config,
      final GroupByQueryResource resource,
      final ObjectMapper spillMapper,
      final String processingTmpDir,
      final int mergeBufferSize,
      final boolean wasQueryPushedDown
  )
  {
    GroupByQuery queryWithRenamedDimensions = null;
    if (wasQueryPushedDown) {
      List<DimensionSpec> newDims = new ArrayList<>(((GroupByQuery) queryParam).getDimensions().size());
      for (DimensionSpec ds : ((GroupByQuery) queryParam).getDimensions()) {
        DimensionSpec dim = new RenamedDimensionSpec(ds);
        newDims.add(dim);
      }
      queryWithRenamedDimensions = ((GroupByQuery) queryParam).withDimensionSpecs(newDims);
    }
    final GroupByQuery query = wasQueryPushedDown ? queryWithRenamedDimensions : (GroupByQuery) queryParam;
    final GroupByQueryConfig querySpecificConfig = config.withOverrides(query);

    final AggregatorFactory[] aggregatorFactories = new AggregatorFactory[query.getAggregatorSpecs().size()];
    for (int i = 0; i < query.getAggregatorSpecs().size(); i++) {
      AggregatorFactory af = query.getAggregatorSpecs().get(i);
      aggregatorFactories[i] = wasQueryPushedDown ? af.getCombiningFactory() : af;
    }

    final File temporaryStorageDirectory = new File(
        processingTmpDir,
        StringUtils.format("druid-groupBy-%s_%s", UUID.randomUUID(), query.getId())
    );

    final List<Interval> queryIntervals = query.getIntervals();
    final Filter filter = Filters.convertToCNFFromQueryContext(
        query,
        Filters.toFilter(query.getDimFilter())
    );

    final SettableSupplier<Row> rowSupplier = new SettableSupplier<>();
    final RowBasedColumnSelectorFactory columnSelectorFactory = RowBasedColumnSelectorFactory.create(
        rowSupplier,
        rowSignature
    );
    final ValueMatcher filterMatcher = filter == null
                                       ? BooleanValueMatcher.of(true)
                                       : filter.makeMatcher(columnSelectorFactory);

    final FilteredSequence<Row> filteredSequence = wasQueryPushedDown ? new FilteredSequence<>(
        rows,
        new Predicate<Row>()
        {
          @Override
          public boolean apply(Row input)
          {
            rowSupplier.set(input);
            return true; // nothing to filter since it has already been done on the push down
          }
        }
    ) : new FilteredSequence<>(
        rows,
        new Predicate<Row>()
        {
          @Override
          public boolean apply(Row input)
          {
            boolean inInterval = false;
            DateTime rowTime = input.getTimestamp();
            for (Interval queryInterval : queryIntervals) {
              if (queryInterval.contains(rowTime)) {
                inInterval = true;
                break;
              }
            }
            if (!inInterval) {
              return false;
            }
            rowSupplier.set(input);
            return filterMatcher.matches();
          }
        }
    );

    return new BaseSequence<>(
        new BaseSequence.IteratorMaker<Row, CloseableGrouperIterator<RowBasedKey, Row>>()
        {
          @Override
          public CloseableGrouperIterator<RowBasedKey, Row> make()
          {
            // This contains all closeable objects which are closed when the returned iterator iterates all the elements,
            // or an exceptions is thrown. The objects are closed in their reverse order.
            final List<Closeable> closeOnExit = Lists.newArrayList();

            try {
              final LimitedTemporaryStorage temporaryStorage = new LimitedTemporaryStorage(
                  temporaryStorageDirectory,
                  querySpecificConfig.getMaxOnDiskStorage()
              );

              closeOnExit.add(temporaryStorage);

              Pair<Grouper<RowBasedKey>, Accumulator<AggregateResult, Row>> pair = RowBasedGrouperHelper.createGrouperAccumulatorPair(
                  query,
                  true,
                  rowSignature,
                  querySpecificConfig,
                  new Supplier<ByteBuffer>()
                  {
                    @Override
                    public ByteBuffer get()
                    {
                      final ResourceHolder<ByteBuffer> mergeBufferHolder = resource.getMergeBuffer();
                      closeOnExit.add(mergeBufferHolder);
                      return mergeBufferHolder.get();
                    }
                  },
                  temporaryStorage,
                  spillMapper,
                  aggregatorFactories,
                  mergeBufferSize
              );
              final Grouper<RowBasedKey> grouper = pair.lhs;
              final Accumulator<AggregateResult, Row> accumulator = pair.rhs;
              closeOnExit.add(grouper);

              final AggregateResult retVal = filteredSequence.accumulate(AggregateResult.ok(), accumulator);
              if (!retVal.isOk()) {
                throw new ResourceLimitExceededException(retVal.getReason());
              }

              return RowBasedGrouperHelper.makeGrouperIterator(
                  grouper,
                  query,
                  new Closeable()
                  {
                    @Override
                    public void close()
                    {
                      for (Closeable closeable : Lists.reverse(closeOnExit)) {
                        CloseQuietly.close(closeable);
                      }
                    }
                  }
              );
            }
            catch (Throwable e) {
              // Exception caught while setting up the iterator; release resources.
              for (Closeable closeable : Lists.reverse(closeOnExit)) {
                CloseQuietly.close(closeable);
              }
              throw e;
            }
          }

          @Override
          public void cleanup(CloseableGrouperIterator<RowBasedKey, Row> iterFromMake)
          {
            iterFromMake.close();
          }
        }
    );

  }

  /**
   * Dimension spec that has the same dimension name as the output name of
   * the delegate dimension spec. This kind of dimension spec comes in handy
   * when the entire query is pushed down to the historical nodes and the
   * broker then has to merge results with the dimension named as output name
   * of the original (delegate) dimension.
   */
  private static class RenamedDimensionSpec implements DimensionSpec
  {
    private final DimensionSpec delegate;

    public RenamedDimensionSpec(DimensionSpec delegate)
    {
      this.delegate = delegate;
    }

    @Override
    public String getDimension()
    {
      // the dimension name is same as the output name.
      return delegate.getOutputName();
    }

    @Override
    public String getOutputName()
    {
      return delegate.getOutputName();
    }

    @Override
    public ValueType getOutputType()
    {
      return delegate.getOutputType();
    }

    @Override
    public ExtractionFn getExtractionFn()
    {
      return delegate.getExtractionFn();
    }

    @Override
    public DimensionSelector decorate(DimensionSelector selector)
    {
      return delegate.decorate(selector);
    }

    @Override
    public boolean mustDecorate()
    {
      return delegate.mustDecorate();
    }

    @Override
    public boolean preservesOrdering()
    {
      return delegate.preservesOrdering();
    }

    @Override
    public byte[] getCacheKey()
    {
      return delegate.getCacheKey();
    }
  }

}
