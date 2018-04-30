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
package io.druid.query.groupby;

import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.common.util.concurrent.ListenableFuture;
import io.druid.collections.BlockingPool;
import io.druid.collections.DefaultBlockingPool;
import io.druid.collections.NonBlockingPool;
import io.druid.collections.StupidPool;
import io.druid.data.input.InputRow;
import io.druid.data.input.MapBasedInputRow;
import io.druid.data.input.Row;
import io.druid.data.input.impl.DimensionsSpec;
import io.druid.data.input.impl.LongDimensionSchema;
import io.druid.data.input.impl.StringDimensionSchema;
import io.druid.jackson.DefaultObjectMapper;
import io.druid.java.util.common.Intervals;
import io.druid.java.util.common.concurrent.Execs;
import io.druid.java.util.common.granularity.Granularities;
import io.druid.java.util.common.guava.Sequence;
import io.druid.java.util.common.guava.Sequences;
import io.druid.java.util.common.logger.Logger;
import io.druid.js.JavaScriptConfig;
import io.druid.math.expr.ExprMacroTable;
import io.druid.query.BySegmentQueryRunner;
import io.druid.query.DruidProcessingConfig;
import io.druid.query.FinalizeResultsQueryRunner;
import io.druid.query.IntervalChunkingQueryRunnerDecorator;
import io.druid.query.Query;
import io.druid.query.QueryPlus;
import io.druid.query.QueryRunner;
import io.druid.query.QueryRunnerFactory;
import io.druid.query.QueryToolChest;
import io.druid.query.QueryWatcher;
import io.druid.query.aggregation.LongMaxAggregatorFactory;
import io.druid.query.aggregation.LongSumAggregatorFactory;
import io.druid.query.aggregation.MetricManipulatorFns;
import io.druid.query.dimension.DefaultDimensionSpec;
import io.druid.query.dimension.DimensionSpec;
import io.druid.query.dimension.ExtractionDimensionSpec;
import io.druid.query.extraction.RegexDimExtractionFn;
import io.druid.query.filter.JavaScriptDimFilter;
import io.druid.query.groupby.having.GreaterThanHavingSpec;
import io.druid.query.groupby.strategy.GroupByStrategy;
import io.druid.query.groupby.strategy.GroupByStrategySelector;
import io.druid.query.groupby.strategy.GroupByStrategyV1;
import io.druid.query.groupby.strategy.GroupByStrategyV2;
import io.druid.query.spec.MultipleIntervalSegmentSpec;
import io.druid.query.spec.QuerySegmentSpec;
import io.druid.segment.IndexIO;
import io.druid.segment.IndexMergerV9;
import io.druid.segment.IndexSpec;
import io.druid.segment.QueryableIndex;
import io.druid.segment.QueryableIndexSegment;
import io.druid.segment.Segment;
import io.druid.segment.column.ColumnConfig;
import io.druid.segment.incremental.IncrementalIndex;
import io.druid.segment.incremental.IncrementalIndexSchema;
import io.druid.segment.writeout.OffHeapMemorySegmentWriteOutMediumFactory;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class NestedQueryPushDownTest
{
  private static final IndexIO INDEX_IO;
  private static final IndexMergerV9 INDEX_MERGER_V9;
  public static final ObjectMapper JSON_MAPPER;
  private File tmpDir;
  private QueryRunnerFactory<Row, GroupByQuery> groupByFactory;
  private QueryRunnerFactory<Row, GroupByQuery> groupByFactory2;
  private List<IncrementalIndex> incrementalIndices = Lists.newArrayList();
  private List<QueryableIndex> groupByIndices = Lists.newArrayList();
  private ExecutorService executorService;

  static {
    JSON_MAPPER = new DefaultObjectMapper();
    JSON_MAPPER.setInjectableValues(
        new InjectableValues.Std().addValue(
            ExprMacroTable.class,
            ExprMacroTable.nil()
        )
    );
    INDEX_IO = new IndexIO(
        JSON_MAPPER,
        OffHeapMemorySegmentWriteOutMediumFactory.instance(),
        new ColumnConfig()
        {
          @Override
          public int columnCacheSizeBytes()
          {
            return 0;
          }
        }
    );
    INDEX_MERGER_V9 = new IndexMergerV9(JSON_MAPPER, INDEX_IO, OffHeapMemorySegmentWriteOutMediumFactory.instance());
  }

  private IncrementalIndex makeIncIndex()
  {
    return new IncrementalIndex.Builder()
        .setIndexSchema(
            new IncrementalIndexSchema.Builder()
                .withDimensionsSpec(new DimensionsSpec(
                    Arrays.asList(
                        new StringDimensionSchema("dimA"),
                        new StringDimensionSchema("dimB"),
                        new LongDimensionSchema("metA"),
                        new LongDimensionSchema("metB")
                    ),
                    null,
                    null
                ))
                //.withMetrics(new CountAggregatorFactory("metA"))
                //.withMetrics(new CountAggregatorFactory("metB"))
                .build()
        )
        .setReportParseExceptions(false)
        .setConcurrentEventAdd(true)
        .setMaxRowCount(1000)
        .buildOnheap();
  }

  @Before
  public void setup() throws Exception

  {
    tmpDir = Files.createTempDir();

    InputRow row;
    List<String> dimNames = Arrays.asList("dimA", "metA", "dimB", "metB");
    Map<String, Object> event;

    final IncrementalIndex indexA = makeIncIndex();
    incrementalIndices.add(indexA);

    event = new HashMap<>();
    event.put("dimA", "pomegranate");
    event.put("metA", 1000L);
    event.put("dimB", "sweet");
    event.put("metB", 10L);
    row = new MapBasedInputRow(1505260888888L, dimNames, event);
    indexA.add(row);

    event = new HashMap<>();
    event.put("dimA", "mango");
    event.put("metA", 1000L);
    event.put("dimB", "sweet");
    event.put("metB", 20L);
    row = new MapBasedInputRow(1505260800000L, dimNames, event);
    indexA.add(row);

    event = new HashMap<>();
    event.put("dimA", "pomegranate");
    event.put("metA", 1000L);
    event.put("dimB", "sweet");
    event.put("metB", 10L);
    row = new MapBasedInputRow(1505264400000L, dimNames, event);
    indexA.add(row);

    event = new HashMap<>();
    event.put("dimA", "mango");
    event.put("metA", 1000L);
    event.put("dimB", "sweet");
    event.put("metB", 20L);
    row = new MapBasedInputRow(1505264400400L, dimNames, event);
    indexA.add(row);

    final File fileA = INDEX_MERGER_V9.persist(
        indexA,
        new File(tmpDir, "A"),
        new IndexSpec(),
        null
    );
    QueryableIndex qindexA = INDEX_IO.loadIndex(fileA);


    final IncrementalIndex indexB = makeIncIndex();
    incrementalIndices.add(indexB);

    event = new HashMap<>();
    event.put("dimA", "pomegranate");
    event.put("metA", 1000L);
    event.put("dimB", "sweet");
    event.put("metB", 10L);
    row = new MapBasedInputRow(1505260800000L, dimNames, event);
    indexB.add(row);

    event = new HashMap<>();
    event.put("dimA", "mango");
    event.put("metA", 1000L);
    event.put("dimB", "sweet");
    event.put("metB", 20L);
    row = new MapBasedInputRow(1505260800000L, dimNames, event);
    indexB.add(row);

    event = new HashMap<>();
    event.put("dimA", "pomegranate");
    event.put("metA", 1000L);
    event.put("dimB", "sour");
    event.put("metB", 10L);
    row = new MapBasedInputRow(1505264400000L, dimNames, event);
    indexB.add(row);

    event = new HashMap<>();
    event.put("dimA", "mango");
    event.put("metA", 1000L);
    event.put("dimB", "sour");
    event.put("metB", 20L);
    row = new MapBasedInputRow(1505264400000L, dimNames, event);
    indexB.add(row);

    final File fileB = INDEX_MERGER_V9.persist(
        indexB,
        new File(tmpDir, "B"),
        new IndexSpec(),
        null
    );
    QueryableIndex qindexB = INDEX_IO.loadIndex(fileB);

    groupByIndices = Arrays.asList(qindexA, qindexB);
    setupGroupByFactory();
  }


  private void setupGroupByFactory()
  {
    executorService = Execs.multiThreaded(3, "GroupByThreadPool[%d]");

    NonBlockingPool<ByteBuffer> bufferPool = new StupidPool<>(
        "GroupByBenchmark-computeBufferPool",
        new OffheapBufferGenerator("compute", 10_000_000),
        0,
        Integer.MAX_VALUE
    );

    // limit of 3 is required since we simulate running historical running nested query and broker doing the final merge
    BlockingPool<ByteBuffer> mergePool = new DefaultBlockingPool<>(
        new OffheapBufferGenerator("merge", 10_000_000),
        10
    );
    // limit of 3 is required since we simulate running historical running nested query and broker doing the final merge
    BlockingPool<ByteBuffer> mergePool2 = new DefaultBlockingPool<>(
        new OffheapBufferGenerator("merge", 10_000_000),
        10
    );

    final GroupByQueryConfig config = new GroupByQueryConfig()
    {
      @Override
      public String getDefaultStrategy()
      {
        return "v2";
      }

      @Override
      public int getBufferGrouperInitialBuckets()
      {
        return -1;
      }

      @Override
      public long getMaxOnDiskStorage()
      {
        return 1_000_000_000L;
      }
    };
    config.setSingleThreaded(false);
    config.setMaxIntermediateRows(Integer.MAX_VALUE);
    config.setMaxResults(Integer.MAX_VALUE);

    DruidProcessingConfig druidProcessingConfig = new DruidProcessingConfig()
    {
      @Override
      public int getNumThreads()
      {
        // Used by "v2" strategy for concurrencyHint
        return 2;
      }

      @Override
      public String getFormatString()
      {
        return null;
      }
    };

    final Supplier<GroupByQueryConfig> configSupplier = Suppliers.ofInstance(config);
    final GroupByStrategySelector strategySelector = new GroupByStrategySelector(
        configSupplier,
        new GroupByStrategyV1(
            configSupplier,
            new GroupByQueryEngine(configSupplier, bufferPool),
            NOOP_QUERYWATCHER,
            bufferPool
        ),
        new GroupByStrategyV2(
            druidProcessingConfig,
            configSupplier,
            bufferPool,
            mergePool,
            new ObjectMapper(new SmileFactory()),
            NOOP_QUERYWATCHER
        )
    );

    final GroupByStrategySelector strategySelector2 = new GroupByStrategySelector(
        configSupplier,
        new GroupByStrategyV1(
            configSupplier,
            new GroupByQueryEngine(configSupplier, bufferPool),
            NOOP_QUERYWATCHER,
            bufferPool
        ),
        new GroupByStrategyV2(
            druidProcessingConfig,
            configSupplier,
            bufferPool,
            mergePool2,
            new ObjectMapper(new SmileFactory()),
            NOOP_QUERYWATCHER
        )
    );

    groupByFactory = new GroupByQueryRunnerFactory(
        strategySelector,
        new GroupByQueryQueryToolChest(
            strategySelector,
            NoopIntervalChunkingQueryRunnerDecorator()
        )
    );

    groupByFactory2 = new GroupByQueryRunnerFactory(
        strategySelector2,
        new GroupByQueryQueryToolChest(
            strategySelector2,
            NoopIntervalChunkingQueryRunnerDecorator()
        )
    );
  }

  @After
  public void tearDown() throws Exception
  {
    for (IncrementalIndex incrementalIndex : incrementalIndices) {
      incrementalIndex.close();
    }

    for (QueryableIndex queryableIndex : groupByIndices) {
      queryableIndex.close();
    }

    if (tmpDir != null) {
      FileUtils.deleteDirectory(tmpDir);
    }
  }

  @Test
  public void testSimpleDoubleAggregation()
  {

    QuerySegmentSpec intervalSpec = new MultipleIntervalSegmentSpec(
        Collections.singletonList(Intervals.utc(1500000000000L, 1600000000000L))
    );

    GroupByQuery query = GroupByQuery
        .builder()
        .setDataSource("blah")
        .setQuerySegmentSpec(intervalSpec)
        .setDimensions(Lists.<DimensionSpec>newArrayList(
            new DefaultDimensionSpec("dimA", "dimA"),
            new DefaultDimensionSpec("dimB", "dimB")
        ))
        .setAggregatorSpecs(
            Arrays.asList(
                new LongSumAggregatorFactory("metASum", "metA"),
                new LongSumAggregatorFactory("metBSum", "metB")
            ))
        .setGranularity(Granularities.ALL)
        .build();

    GroupByQuery nestedQuery = GroupByQuery
        .builder()
        .setDataSource(query)
        .setQuerySegmentSpec(intervalSpec)
        .setDimensions(Lists.newArrayList(
            new DefaultDimensionSpec("dimB", "dimB")
        ))
        .setAggregatorSpecs(
            Arrays.asList(new LongSumAggregatorFactory("totalSum", "metASum"))
        )
        .setContext(
            ImmutableMap.of(
                GroupByQueryConfig.CTX_KEY_FORCE_NESTED_QUERY_PUSH_DOWN, true
            )
        )
        .setGranularity(Granularities.ALL)
        .build();

    Sequence<Row> queryResult = runNestedQueryWithForcePushDown(nestedQuery, Maps.newHashMap());
    List<Row> results = queryResult.toList();

    Row expectedRow0 = GroupByQueryRunnerTestHelper.createExpectedRow(
        "2017-07-14T02:40:00.000Z",
        "dimB", "sour",
        "totalSum", 2000L
    );
    Row expectedRow1 = GroupByQueryRunnerTestHelper.createExpectedRow(
        "2017-07-14T02:40:00.000Z",
        "dimB", "sweet",
        "totalSum", 6000L
    );

    Assert.assertEquals(2, results.size());
    Assert.assertEquals(expectedRow0, results.get(0));
    Assert.assertEquals(expectedRow1, results.get(1));
  }

  @Test
  public void testNestedQueryWithRenamedDimensions()
  {

    QuerySegmentSpec intervalSpec = new MultipleIntervalSegmentSpec(
        Collections.singletonList(Intervals.utc(1500000000000L, 1600000000000L))
    );

    GroupByQuery query = GroupByQuery
        .builder()
        .setDataSource("blah")
        .setQuerySegmentSpec(intervalSpec)
        .setDimensions(Lists.newArrayList(
            new DefaultDimensionSpec("dimA", "dimA"),
            new DefaultDimensionSpec("dimB", "newDimB")
        ))
        .setAggregatorSpecs(
            Arrays.asList(
                new LongSumAggregatorFactory("metASum", "metA"),
                new LongSumAggregatorFactory("metBSum", "metB")
            ))
        .setGranularity(Granularities.ALL)
        .build();

    GroupByQuery nestedQuery = GroupByQuery
        .builder()
        .setDataSource(query)
        .setQuerySegmentSpec(intervalSpec)
        .setDimensions(Lists.newArrayList(
            new DefaultDimensionSpec("newDimB", "renamedDimB")
        ))
        .setAggregatorSpecs(
            Arrays.asList(new LongMaxAggregatorFactory("maxBSum", "metBSum"))
        )
        .setContext(
            ImmutableMap.of(
                GroupByQueryConfig.CTX_KEY_FORCE_NESTED_QUERY_PUSH_DOWN, true
            )
        )
        .setGranularity(Granularities.ALL)
        .build();

    Sequence<Row> queryResult = runNestedQueryWithForcePushDown(nestedQuery, Maps.newHashMap());
    List<Row> results = queryResult.toList();

    Row expectedRow0 = GroupByQueryRunnerTestHelper.createExpectedRow(
        "2017-07-14T02:40:00.000Z",
        "renamedDimB", "sour",
        "maxBSum", 20L
    );
    Row expectedRow1 = GroupByQueryRunnerTestHelper.createExpectedRow(
        "2017-07-14T02:40:00.000Z",
        "renamedDimB", "sweet",
        "maxBSum", 60L
    );
    Assert.assertEquals(2, results.size());
    Assert.assertEquals(expectedRow0, results.get(0));
    Assert.assertEquals(expectedRow1, results.get(1));
  }

  @Test
  public void testDimensionFilterOnOuterAndInnerQueries()
  {
    QuerySegmentSpec intervalSpec = new MultipleIntervalSegmentSpec(
        Collections.singletonList(Intervals.utc(1500000000000L, 1600000000000L))
    );
    GroupByQuery query = GroupByQuery
        .builder()
        .setDataSource("blah")
        .setDimensions(Lists.<DimensionSpec>newArrayList(
            new DefaultDimensionSpec("dimA", "dimA"),
            new DefaultDimensionSpec("dimB", "dimB")
        ))
        .setAggregatorSpecs(
            Arrays.asList(
                new LongSumAggregatorFactory("metASum", "metA"),
                new LongSumAggregatorFactory("metBSum", "metB")
            ))
        .setGranularity(Granularities.ALL)
        .setQuerySegmentSpec(intervalSpec)
        .setDimFilter(new JavaScriptDimFilter(
            "dimA",
            "function(dim){ return dim == 'mango' }",
            null,
            JavaScriptConfig.getEnabledInstance()
        ))
        .build();

    GroupByQuery nestedQuery = GroupByQuery
        .builder()
        .setDataSource(query)
        .setDimensions(Lists.<DimensionSpec>newArrayList(
            new DefaultDimensionSpec("dimA", "newDimA")
        ))
        .setAggregatorSpecs(
            Arrays.asList(new LongSumAggregatorFactory("finalSum", "metASum"))
        )
        .setContext(
            ImmutableMap.of(
                GroupByQueryConfig.CTX_KEY_FORCE_NESTED_QUERY_PUSH_DOWN, true
            )
        )
        .setGranularity(Granularities.ALL)
        .setDimFilter(new JavaScriptDimFilter(
            "dimA",
            "function(dim){ return dim == 'pomegranate' }",
            null,
            JavaScriptConfig.getEnabledInstance()
        ))
        .setQuerySegmentSpec(intervalSpec)
        .build();

    Sequence<Row> queryResult = runNestedQueryWithForcePushDown(nestedQuery, Maps.newHashMap());
    List<Row> results = queryResult.toList();

    Assert.assertEquals(0, results.size());
  }

  @Test
  public void testDimensionFilterOnOuterQuery()
  {
    QuerySegmentSpec intervalSpec = new MultipleIntervalSegmentSpec(
        Collections.singletonList(Intervals.utc(1500000000000L, 1600000000000L))
    );
    GroupByQuery query = GroupByQuery
        .builder()
        .setDataSource("blah")
        .setDimensions(Lists.<DimensionSpec>newArrayList(
            new DefaultDimensionSpec("dimA", "dimA"),
            new DefaultDimensionSpec("dimB", "dimB")
        ))
        .setAggregatorSpecs(
            Arrays.asList(
                new LongSumAggregatorFactory("metASum", "metA"),
                new LongSumAggregatorFactory("metBSum", "metB")
            ))
        .setGranularity(Granularities.ALL)
        .setQuerySegmentSpec(intervalSpec)
        .build();

    GroupByQuery nestedQuery = GroupByQuery
        .builder()
        .setDataSource(query)
        .setDimensions(Lists.<DimensionSpec>newArrayList(
            new DefaultDimensionSpec("dimA", "newDimA")
        ))
        .setAggregatorSpecs(
            Arrays.asList(new LongSumAggregatorFactory("finalSum", "metASum"))
        )
        .setContext(
            ImmutableMap.of(
                GroupByQueryConfig.CTX_KEY_FORCE_NESTED_QUERY_PUSH_DOWN, true
            )
        )
        .setGranularity(Granularities.ALL)
        .setDimFilter(new JavaScriptDimFilter(
            "dimA",
            "function(dim){ return dim == 'mango' }",
            null,
            JavaScriptConfig.getEnabledInstance()
        ))
        .setQuerySegmentSpec(intervalSpec)
        .build();

    Row expectedRow0 = GroupByQueryRunnerTestHelper.createExpectedRow(
        "2017-07-14T02:40:00.000Z",
        "finalSum", 4000L,
        "newDimA", "mango"
    );
    Sequence<Row> queryResult = runNestedQueryWithForcePushDown(nestedQuery, Maps.newHashMap());
    List<Row> results = queryResult.toList();

    Assert.assertEquals(1, results.size());
    Assert.assertEquals(expectedRow0, results.get(0));
  }

  @Test
  public void testDimensionFilterOnInnerQuery()
  {
    QuerySegmentSpec intervalSpec = new MultipleIntervalSegmentSpec(
        Collections.singletonList(Intervals.utc(1500000000000L, 1600000000000L))
    );
    GroupByQuery query = GroupByQuery
        .builder()
        .setDataSource("blah")
        .setDimensions(Lists.<DimensionSpec>newArrayList(
            new DefaultDimensionSpec("dimA", "dimA"),
            new DefaultDimensionSpec("dimB", "dimB")
        ))
        .setAggregatorSpecs(
            Arrays.asList(
                new LongSumAggregatorFactory("metASum", "metA"),
                new LongSumAggregatorFactory("metBSum", "metB")
            ))
        .setGranularity(Granularities.ALL)
        .setQuerySegmentSpec(intervalSpec)
        .setDimFilter(new JavaScriptDimFilter(
            "dimA",
            "function(dim){ return dim == 'mango' }",
            null,
            JavaScriptConfig.getEnabledInstance()
        ))
        .build();

    GroupByQuery nestedQuery = GroupByQuery
        .builder()
        .setDataSource(query)
        .setDimensions(Lists.<DimensionSpec>newArrayList(
            new DefaultDimensionSpec("dimA", "newDimA")
        ))
        .setAggregatorSpecs(
            Arrays.asList(new LongSumAggregatorFactory("finalSum", "metASum"))
        )
        .setContext(
            ImmutableMap.of(
                GroupByQueryConfig.CTX_KEY_FORCE_NESTED_QUERY_PUSH_DOWN, true
            )
        )
        .setGranularity(Granularities.ALL)
        .setQuerySegmentSpec(intervalSpec)
        .build();

    Row expectedRow0 = GroupByQueryRunnerTestHelper.createExpectedRow(
        "2017-07-14T02:40:00.000Z",
        "finalSum", 4000L,
        "newDimA", "mango"
    );
    Sequence<Row> queryResult = runNestedQueryWithForcePushDown(nestedQuery, Maps.newHashMap());
    List<Row> results = queryResult.toList();

    Assert.assertEquals(1, results.size());
    Assert.assertEquals(expectedRow0, results.get(0));
  }

  @Test
  public void testSubqueryWithExtractionFnInOuterQuery()
  {
    QuerySegmentSpec intervalSpec = new MultipleIntervalSegmentSpec(
        Collections.singletonList(Intervals.utc(1500000000000L, 1600000000000L))
    );
    GroupByQuery query = GroupByQuery
        .builder()
        .setDataSource("blah")
        .setDimensions(Lists.<DimensionSpec>newArrayList(
            new DefaultDimensionSpec("dimA", "dimA"),
            new DefaultDimensionSpec("dimB", "dimB")
        ))
        .setAggregatorSpecs(
            Arrays.asList(
                new LongSumAggregatorFactory("metASum", "metA"),
                new LongSumAggregatorFactory("metBSum", "metB")
            ))
        .setGranularity(Granularities.ALL)
        .setQuerySegmentSpec(intervalSpec)
        .build();

    GroupByQuery nestedQuery = GroupByQuery
        .builder()
        .setDataSource(query)
        .setDimensions(Lists.<DimensionSpec>newArrayList(
            new DefaultDimensionSpec("dimA", "newDimA")
        ))
        .setDimensions(Lists.<DimensionSpec>newArrayList(
            new ExtractionDimensionSpec(
                "dimA",
                "extractedDimA",
                new RegexDimExtractionFn("^(p)", true, "replacement")
            )
                       )
        )
        .setAggregatorSpecs(
            Arrays.asList(new LongSumAggregatorFactory("finalSum", "metASum"))
        )
        .setContext(
            ImmutableMap.of(
                GroupByQueryConfig.CTX_KEY_FORCE_NESTED_QUERY_PUSH_DOWN, true
            )
        )
        .setGranularity(Granularities.ALL)
        .setQuerySegmentSpec(intervalSpec)
        .build();

    Row expectedRow0 = GroupByQueryRunnerTestHelper.createExpectedRow(
        "2017-07-14T02:40:00.000Z",
        "finalSum", 4000L,
        "extractedDimA", "p"
    );
    Row expectedRow1 = GroupByQueryRunnerTestHelper.createExpectedRow(
        "2017-07-14T02:40:00.000Z",
        "finalSum", 4000L,
        "extractedDimA", "replacement"
    );
    Sequence<Row> queryResult = runNestedQueryWithForcePushDown(nestedQuery, Maps.newHashMap());
    List<Row> results = queryResult.toList();

    Assert.assertEquals(2, results.size());
    Assert.assertEquals(expectedRow0, results.get(0));
    Assert.assertEquals(expectedRow1, results.get(1));
  }

  @Test
  public void testHavingClauseInNestedPushDownQuery()
  {
    QuerySegmentSpec intervalSpec = new MultipleIntervalSegmentSpec(
        Collections.singletonList(Intervals.utc(1500000000000L, 1600000000000L))
    );
    GroupByQuery innerQuery = GroupByQuery
        .builder()
        .setDataSource("blah")
        .setDimensions(Lists.<DimensionSpec>newArrayList(
            new DefaultDimensionSpec("dimA", "dimA"),
            new DefaultDimensionSpec("dimB", "dimB")
        ))
        .setAggregatorSpecs(
            Arrays.asList(
                new LongSumAggregatorFactory("metASum", "metA"),
                new LongSumAggregatorFactory("metBSum", "metB")
            ))
        .setGranularity(Granularities.ALL)
        .setQuerySegmentSpec(intervalSpec)
        .build();

    GroupByQuery nestedQuery = GroupByQuery
        .builder()
        .setDataSource(innerQuery)
        .setDimensions(Lists.<DimensionSpec>newArrayList(
            new DefaultDimensionSpec("dimB", "dimB")
        ))
        .setAggregatorSpecs(
            Arrays.asList(new LongSumAggregatorFactory("finalSum", "metBSum"))
        )
        .setHavingSpec(new GreaterThanHavingSpec("finalSum", 70L))
        .setContext(
            ImmutableMap.of(
                GroupByQueryConfig.CTX_KEY_FORCE_NESTED_QUERY_PUSH_DOWN, true
            )
        )
        .setGranularity(Granularities.ALL)
        .setQuerySegmentSpec(intervalSpec)
        .build();

    Row expectedRow0 = GroupByQueryRunnerTestHelper.createExpectedRow(
        "2017-07-14T02:40:00.000Z",
        "dimB", "sweet",
        "finalSum", 90L
    );
    Sequence<Row> queryResult = runNestedQueryWithForcePushDown(nestedQuery, Maps.newHashMap());
    List<Row> results = queryResult.toList();

    Assert.assertEquals(1, results.size());
    Assert.assertEquals(expectedRow0, results.get(0));
  }

  private Sequence<Row> runNestedQueryWithForcePushDown(GroupByQuery nestedQuery, Map<String, Object> context)
  {
    QueryToolChest<Row, GroupByQuery> toolChest = groupByFactory.getToolchest();
    GroupByQuery pushDownQuery = ((GroupByQueryQueryToolChest) toolChest).getNestedQueryToPushDown(nestedQuery);
    // Disable any more push downs

    QueryRunner<Row> segment1Runner = new FinalizeResultsQueryRunner<Row>(
        toolChest.mergeResults(
            groupByFactory.mergeRunners(executorService, getQueryRunnerForSegment1())
        ),
        (QueryToolChest) toolChest
    );

    QueryRunner<Row> segment2Runner = new FinalizeResultsQueryRunner<Row>(
        toolChest.mergeResults(
            groupByFactory2.mergeRunners(executorService, getQueryRunnerForSegment2())
        ),
        (QueryToolChest) toolChest
    );

    QueryRunner<Row> queryRunnerForSegments = new FinalizeResultsQueryRunner<>(
        toolChest.mergeResults(
            new QueryRunner<Row>()
            {
              @Override
              public Sequence<Row> run(QueryPlus<Row> queryPlus, Map<String, Object> responseContext)
              {
                return Sequences
                    .simple(
                        ImmutableList.of(
                            Sequences.map(
                                segment1Runner.run(queryPlus, responseContext),
                                toolChest.makePreComputeManipulatorFn(
                                    (GroupByQuery) queryPlus.getQuery(),
                                    MetricManipulatorFns.deserializing()
                                )
                            ),
                            Sequences.map(
                                segment2Runner.run(queryPlus, responseContext),
                                toolChest.makePreComputeManipulatorFn(
                                    (GroupByQuery) queryPlus.getQuery(),
                                    MetricManipulatorFns.deserializing()
                                )
                            )
                        )
                    )
                    .flatMerge(Function.identity(), queryPlus.getQuery().getResultOrdering());
              }
            }
        ),
        (QueryToolChest) toolChest
    );
    GroupByStrategy strategy = ((GroupByQueryRunnerFactory) groupByFactory).getStrategySelector()
                                                                           .strategize(nestedQuery);
    // Historicals execute the query with force push down flag as false
    GroupByQuery queryWithPushDownDisabled = pushDownQuery.withOverriddenContext(ImmutableMap.of(
        GroupByQueryConfig.CTX_KEY_FORCE_NESTED_QUERY_PUSH_DOWN,
        false
    ));
    Sequence<Row> pushDownQueryResults = strategy.mergeResults(
        queryRunnerForSegments,
        queryWithPushDownDisabled,
        context
    );
    // Broker executes this code and hence has
    return strategy.processSubqueryResult(
        queryWithPushDownDisabled,
        nestedQuery,
        strategy.prepareResource(nestedQuery, false),
        pushDownQueryResults,
        true
    );

  }

  private List<QueryRunner<Row>> getRunner1()
  {
    List<QueryRunner<Row>> runners = Lists.newArrayList();
    QueryableIndex index = groupByIndices.get(0);
    QueryRunner<Row> runner = makeQueryRunner(
        groupByFactory,
        index.toString(),
        new QueryableIndexSegment(index.toString(), index)
    );
    runners.add(groupByFactory.getToolchest().preMergeQueryDecoration(runner));
    return runners;
  }

  private List<QueryRunner<Row>> getRunner2()
  {
    List<QueryRunner<Row>> runners = Lists.newArrayList();
    QueryableIndex index2 = groupByIndices.get(1);
    QueryRunner<Row> tooSmallRunner = makeQueryRunner(
        groupByFactory2,
        index2.toString(),
        new QueryableIndexSegment(index2.toString(), index2)
    );
    runners.add(groupByFactory2.getToolchest().preMergeQueryDecoration(tooSmallRunner));
    return runners;
  }

  public static <T, QueryType extends Query<T>> QueryRunner<T> makeQueryRunner(
      QueryRunnerFactory<T, QueryType> factory,
      String segmentId,
      Segment adapter
  )
  {
    return new FinalizeResultsQueryRunner<T>(
        new BySegmentQueryRunner<T>(
            segmentId, adapter.getDataInterval().getStart(),
            factory.createRunner(adapter)
        ),
        (QueryToolChest<T, Query<T>>) factory.getToolchest()
    );
  }


  private List<QueryRunner<Row>> getQueryRunnerForSegment1()
  {
    List<QueryRunner<Row>> runners = Lists.newArrayList();
    QueryableIndex index = groupByIndices.get(0);
    QueryRunner<Row> runner = makeQueryRunnerForSegment(
        groupByFactory,
        index.toString(),
        new QueryableIndexSegment(index.toString(), index)
    );
    runners.add(groupByFactory.getToolchest().preMergeQueryDecoration(runner));
    return runners;
  }

  private List<QueryRunner<Row>> getQueryRunnerForSegment2()
  {
    List<QueryRunner<Row>> runners = Lists.newArrayList();
    QueryableIndex index2 = groupByIndices.get(1);
    QueryRunner<Row> tooSmallRunner = makeQueryRunnerForSegment(
        groupByFactory2,
        index2.toString(),
        new QueryableIndexSegment(index2.toString(), index2)
    );
    runners.add(groupByFactory2.getToolchest().preMergeQueryDecoration(tooSmallRunner));
    return runners;
  }

  private static class OffheapBufferGenerator implements Supplier<ByteBuffer>
  {
    private static final Logger log = new Logger(OffheapBufferGenerator.class);

    private final String description;
    private final int computationBufferSize;
    private final AtomicLong count = new AtomicLong(0);

    public OffheapBufferGenerator(String description, int computationBufferSize)
    {
      this.description = description;
      this.computationBufferSize = computationBufferSize;
    }

    @Override
    public ByteBuffer get()
    {
      log.info(
          "Allocating new %s buffer[%,d] of size[%,d]",
          description,
          count.getAndIncrement(),
          computationBufferSize
      );

      return ByteBuffer.allocateDirect(computationBufferSize);
    }
  }

  public static <T, QueryType extends Query<T>> QueryRunner<T> makeQueryRunnerForSegment(
      QueryRunnerFactory<T, QueryType> factory,
      String segmentId,
      Segment adapter
  )
  {
    return new FinalizeResultsQueryRunner<>(
        new BySegmentQueryRunner<>(
            segmentId, adapter.getDataInterval().getStart(),
            factory.createRunner(adapter)
        ),
        (QueryToolChest<T, Query<T>>) factory.getToolchest()
    );
  }

  public static final QueryWatcher NOOP_QUERYWATCHER = new QueryWatcher()
  {
    @Override
    public void registerQuery(Query query, ListenableFuture future)
    {

    }
  };

  public static IntervalChunkingQueryRunnerDecorator NoopIntervalChunkingQueryRunnerDecorator()
  {
    return new IntervalChunkingQueryRunnerDecorator(null, null, null)
    {
      @Override
      public <T> QueryRunner<T> decorate(final QueryRunner<T> delegate, QueryToolChest<T, ? extends Query<T>> toolChest)
      {
        return new QueryRunner<T>()
        {
          @Override
          public Sequence<T> run(QueryPlus<T> queryPlus, Map<String, Object> responseContext)
          {
            return delegate.run(queryPlus, responseContext);
          }
        };
      }
    };
  }

}
