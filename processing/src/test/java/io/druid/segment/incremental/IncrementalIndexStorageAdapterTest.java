/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.druid.segment.incremental;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import io.druid.collections.CloseableStupidPool;
import io.druid.common.config.NullHandling;
import io.druid.data.input.MapBasedInputRow;
import io.druid.data.input.MapBasedRow;
import io.druid.data.input.Row;
import io.druid.java.util.common.DateTimes;
import io.druid.java.util.common.Intervals;
import io.druid.java.util.common.granularity.Granularities;
import io.druid.java.util.common.guava.Sequence;
import io.druid.js.JavaScriptConfig;
import io.druid.query.Result;
import io.druid.query.aggregation.CountAggregatorFactory;
import io.druid.query.aggregation.JavaScriptAggregatorFactory;
import io.druid.query.aggregation.LongSumAggregatorFactory;
import io.druid.query.dimension.DefaultDimensionSpec;
import io.druid.query.filter.DimFilters;
import io.druid.query.groupby.GroupByQuery;
import io.druid.query.groupby.GroupByQueryConfig;
import io.druid.query.groupby.GroupByQueryEngine;
import io.druid.query.topn.TopNQueryBuilder;
import io.druid.query.topn.TopNQueryEngine;
import io.druid.query.topn.TopNResultValue;
import io.druid.segment.Cursor;
import io.druid.segment.DimensionSelector;
import io.druid.segment.StorageAdapter;
import io.druid.segment.VirtualColumns;
import io.druid.segment.data.IndexedInts;
import io.druid.segment.filter.SelectorFilter;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 */
@RunWith(Parameterized.class)
public class IncrementalIndexStorageAdapterTest
{
  interface IndexCreator
  {
    IncrementalIndex createIndex();
  }

  private final IndexCreator indexCreator;

  public IncrementalIndexStorageAdapterTest(
      IndexCreator IndexCreator
  )
  {
    this.indexCreator = IndexCreator;
  }

  @Parameterized.Parameters
  public static Collection<?> constructorFeeder()
  {
    return Arrays.asList(
        new Object[][]{
            {
                new IndexCreator()
                {
                  @Override
                  public IncrementalIndex createIndex()
                  {
                    return new IncrementalIndex.Builder()
                        .setSimpleTestingIndexSchema(new CountAggregatorFactory("cnt"))
                        .setMaxRowCount(1000)
                        .buildOnheap();
                  }
                }
            }
        }
    );
  }

  @Test
  public void testSanity() throws Exception
  {
    IncrementalIndex index = indexCreator.createIndex();
    index.add(
        new MapBasedInputRow(
            System.currentTimeMillis() - 1,
            Collections.singletonList("billy"),
            ImmutableMap.of("billy", "hi")
        )
    );
    index.add(
        new MapBasedInputRow(
            System.currentTimeMillis() - 1,
            Collections.singletonList("sally"),
            ImmutableMap.of("sally", "bo")
        )
    );


    try (
        CloseableStupidPool<ByteBuffer> pool = new CloseableStupidPool<>(
            "GroupByQueryEngine-bufferPool",
            () -> ByteBuffer.allocate(50000)
        )
    ) {
      final GroupByQueryEngine engine = new GroupByQueryEngine(
          Suppliers.ofInstance(
              new GroupByQueryConfig()
              {
                @Override
                public int getMaxIntermediateRows()
                {
                  return 5;
                }
              }
          ),
          pool
      );

      final Sequence<Row> rows = engine.process(
          GroupByQuery.builder()
                      .setDataSource("test")
                      .setGranularity(Granularities.ALL)
                      .setInterval(new Interval(DateTimes.EPOCH, DateTimes.nowUtc()))
                      .addDimension("billy")
                      .addDimension("sally")
                      .addAggregator(new LongSumAggregatorFactory("cnt", "cnt"))
                      .build(),
          new IncrementalIndexStorageAdapter(index)
      );

      final List<Row> results = rows.toList();

      Assert.assertEquals(2, results.size());

      MapBasedRow row = (MapBasedRow) results.get(0);
      Assert.assertEquals(ImmutableMap.of("sally", "bo", "cnt", 1L), row.getEvent());

      row = (MapBasedRow) results.get(1);
      Assert.assertEquals(ImmutableMap.of("billy", "hi", "cnt", 1L), row.getEvent());
    }
  }

  @Test
  public void testObjectColumnSelectorOnVaryingColumnSchema() throws Exception
  {
    IncrementalIndex index = indexCreator.createIndex();
    index.add(
        new MapBasedInputRow(
            DateTimes.of("2014-09-01T00:00:00"),
            Collections.singletonList("billy"),
            ImmutableMap.of("billy", "hi")
        )
    );
    index.add(
        new MapBasedInputRow(
            DateTimes.of("2014-09-01T01:00:00"),
            Lists.newArrayList("billy", "sally"),
            ImmutableMap.of(
                "billy", "hip",
                "sally", "hop"
            )
        )
    );

    try (
        CloseableStupidPool<ByteBuffer> pool = new CloseableStupidPool<>(
            "GroupByQueryEngine-bufferPool",
            () -> ByteBuffer.allocate(50000)
        )
    ) {
      final GroupByQueryEngine engine = new GroupByQueryEngine(
          Suppliers.ofInstance(
              new GroupByQueryConfig()
              {
                @Override
                public int getMaxIntermediateRows()
                {
                  return 5;
                }
              }
          ),
          pool
      );

      final Sequence<Row> rows = engine.process(
          GroupByQuery.builder()
                      .setDataSource("test")
                      .setGranularity(Granularities.ALL)
                      .setInterval(new Interval(DateTimes.EPOCH, DateTimes.nowUtc()))
                      .addDimension("billy")
                      .addDimension("sally")
                      .addAggregator(
                          new LongSumAggregatorFactory("cnt", "cnt")
                      )
                      .addAggregator(
                          new JavaScriptAggregatorFactory(
                              "fieldLength",
                              Arrays.asList("sally", "billy"),
                              "function(current, s, b) { return current + (s == null ? 0 : s.length) + (b == null ? 0 : b.length); }",
                              "function() { return 0; }",
                              "function(a,b) { return a + b; }",
                              JavaScriptConfig.getEnabledInstance()
                          )
                      )
                      .build(),
          new IncrementalIndexStorageAdapter(index)
      );

      final List<Row> results = rows.toList();

      Assert.assertEquals(2, results.size());

      MapBasedRow row = (MapBasedRow) results.get(0);
      Assert.assertEquals(ImmutableMap.of("billy", "hi", "cnt", 1L, "fieldLength", 2.0), row.getEvent());

      row = (MapBasedRow) results.get(1);
      Assert.assertEquals(
          ImmutableMap.of("billy", "hip", "sally", "hop", "cnt", 1L, "fieldLength", 6.0),
          row.getEvent()
      );
    }
  }

  @Test
  public void testResetSanity() throws IOException
  {

    IncrementalIndex index = indexCreator.createIndex();
    DateTime t = DateTimes.nowUtc();
    Interval interval = new Interval(t.minusMinutes(1), t.plusMinutes(1));

    index.add(
        new MapBasedInputRow(
            t.minus(1).getMillis(),
            Collections.singletonList("billy"),
            ImmutableMap.of("billy", "hi")
        )
    );
    index.add(
        new MapBasedInputRow(
            t.minus(1).getMillis(),
            Collections.singletonList("sally"),
            ImmutableMap.of("sally", "bo")
        )
    );

    IncrementalIndexStorageAdapter adapter = new IncrementalIndexStorageAdapter(index);

    for (boolean descending : Arrays.asList(false, true)) {
      Sequence<Cursor> cursorSequence = adapter.makeCursors(
          new SelectorFilter("sally", "bo"),
          interval,
          VirtualColumns.EMPTY,
          Granularities.NONE,
          descending,
          null
      );

      Cursor cursor = cursorSequence.limit(1).toList().get(0);
      DimensionSelector dimSelector;

      dimSelector = cursor
          .getColumnSelectorFactory()
          .makeDimensionSelector(new DefaultDimensionSpec("sally", "sally"));
      Assert.assertEquals("bo", dimSelector.lookupName(dimSelector.getRow().get(0)));

      index.add(
          new MapBasedInputRow(
              t.minus(1).getMillis(),
              Collections.singletonList("sally"),
              ImmutableMap.of("sally", "ah")
          )
      );

      // Cursor reset should not be affected by out of order values
      cursor.reset();

      dimSelector = cursor
          .getColumnSelectorFactory()
          .makeDimensionSelector(new DefaultDimensionSpec("sally", "sally"));
      Assert.assertEquals("bo", dimSelector.lookupName(dimSelector.getRow().get(0)));
    }
  }

  @Test
  public void testSingleValueTopN() throws IOException
  {
    IncrementalIndex index = indexCreator.createIndex();
    DateTime t = DateTimes.nowUtc();
    index.add(
        new MapBasedInputRow(
            t.minus(1).getMillis(),
            Collections.singletonList("sally"),
            ImmutableMap.of("sally", "bo")
        )
    );

    try (
        CloseableStupidPool<ByteBuffer> pool = new CloseableStupidPool<>(
            "TopNQueryEngine-bufferPool",
            () -> ByteBuffer.allocate(50000)
        )
    ) {
      TopNQueryEngine engine = new TopNQueryEngine(pool);

      final Iterable<Result<TopNResultValue>> results = engine
          .query(
              new TopNQueryBuilder()
                  .dataSource("test")
                  .granularity(Granularities.ALL)
                  .intervals(Collections.singletonList(new Interval(DateTimes.EPOCH, DateTimes.nowUtc())))
                  .dimension("sally")
                  .metric("cnt")
                  .threshold(10)
                  .aggregators(Collections.singletonList(new LongSumAggregatorFactory("cnt", "cnt")))
                  .build(),
              new IncrementalIndexStorageAdapter(index),
              null
          )
          .toList();

      Assert.assertEquals(1, Iterables.size(results));
      Assert.assertEquals(1, results.iterator().next().getValue().getValue().size());
    }
  }

  @Test
  public void testFilterByNull() throws Exception
  {
    IncrementalIndex index = indexCreator.createIndex();
    index.add(
        new MapBasedInputRow(
            System.currentTimeMillis() - 1,
            Collections.singletonList("billy"),
            ImmutableMap.of("billy", "hi")
        )
    );
    index.add(
        new MapBasedInputRow(
            System.currentTimeMillis() - 1,
            Collections.singletonList("sally"),
            ImmutableMap.of("sally", "bo")
        )
    );

    try (
        CloseableStupidPool<ByteBuffer> pool = new CloseableStupidPool<>(
            "GroupByQueryEngine-bufferPool",
            () -> ByteBuffer.allocate(50000)
        )
    ) {
      final GroupByQueryEngine engine = new GroupByQueryEngine(
          Suppliers.ofInstance(
              new GroupByQueryConfig()
              {
                @Override
                public int getMaxIntermediateRows()
                {
                  return 5;
                }
              }
          ),
          pool
      );

      final Sequence<Row> rows = engine.process(
          GroupByQuery.builder()
                      .setDataSource("test")
                      .setGranularity(Granularities.ALL)
                      .setInterval(new Interval(DateTimes.EPOCH, DateTimes.nowUtc()))
                      .addDimension("billy")
                      .addDimension("sally")
                      .addAggregator(new LongSumAggregatorFactory("cnt", "cnt"))
                      .setDimFilter(DimFilters.dimEquals("sally", (String) null))
                      .build(),
          new IncrementalIndexStorageAdapter(index)
      );

      final List<Row> results = rows.toList();

      Assert.assertEquals(1, results.size());

      MapBasedRow row = (MapBasedRow) results.get(0);
      Assert.assertEquals(ImmutableMap.of("billy", "hi", "cnt", 1L), row.getEvent());
    }
  }

  @Test
  public void testCursoringAndIndexUpdationInterleaving() throws Exception
  {
    final IncrementalIndex index = indexCreator.createIndex();
    final long timestamp = System.currentTimeMillis();

    for (int i = 0; i < 2; i++) {
      index.add(
          new MapBasedInputRow(
              timestamp,
              Collections.singletonList("billy"),
              ImmutableMap.of("billy", "v1" + i)
          )
      );
    }

    final StorageAdapter sa = new IncrementalIndexStorageAdapter(index);

    Sequence<Cursor> cursors = sa.makeCursors(
        null,
        Intervals.utc(timestamp - 60_000, timestamp + 60_000),
        VirtualColumns.EMPTY,
        Granularities.ALL,
        false,
        null
    );
    final AtomicInteger assertCursorsNotEmpty = new AtomicInteger(0);

    cursors
        .map(cursor -> {
          DimensionSelector dimSelector = cursor
              .getColumnSelectorFactory()
              .makeDimensionSelector(new DefaultDimensionSpec("billy", "billy"));
          int cardinality = dimSelector.getValueCardinality();

          //index gets more rows at this point, while other thread is iterating over the cursor
          try {
            for (int i = 0; i < 1; i++) {
              index.add(new MapBasedInputRow(timestamp, Collections.singletonList("billy"), ImmutableMap.of("billy", "v2" + i)));
            }
          }
          catch (Exception ex) {
            throw new RuntimeException(ex);
          }

          int rowNumInCursor = 0;
          // and then, cursoring continues in the other thread
          while (!cursor.isDone()) {
            IndexedInts row = dimSelector.getRow();
            row.forEach(i -> Assert.assertTrue(i < cardinality));
            cursor.advance();
            rowNumInCursor++;
          }
          Assert.assertEquals(2, rowNumInCursor);
          assertCursorsNotEmpty.incrementAndGet();

          return null;
        })
        .toList();
    Assert.assertEquals(1, assertCursorsNotEmpty.get());
  }

  @Test
  public void testCursoringAndSnapshot() throws Exception
  {
    final IncrementalIndex index = indexCreator.createIndex();
    final long timestamp = System.currentTimeMillis();

    for (int i = 0; i < 2; i++) {
      index.add(
          new MapBasedInputRow(
              timestamp,
              Collections.singletonList("billy"),
              ImmutableMap.of("billy", "v0" + i)
          )
      );
    }

    final StorageAdapter sa = new IncrementalIndexStorageAdapter(index);

    Sequence<Cursor> cursors = sa.makeCursors(
        null,
        Intervals.utc(timestamp - 60_000, timestamp + 60_000),
        VirtualColumns.EMPTY,
        Granularities.ALL,
        false,
        null
    );
    final AtomicInteger assertCursorsNotEmpty = new AtomicInteger(0);

    cursors
        .map(cursor -> {
          DimensionSelector dimSelector1A = cursor
              .getColumnSelectorFactory()
              .makeDimensionSelector(new DefaultDimensionSpec("billy", "billy"));
          int cardinalityA = dimSelector1A.getValueCardinality();

          //index gets more rows at this point, while other thread is iterating over the cursor
          try {
            index.add(new MapBasedInputRow(timestamp, Collections.singletonList("billy"), ImmutableMap.of("billy", "v1")));
          }
          catch (Exception ex) {
            throw new RuntimeException(ex);
          }

          DimensionSelector dimSelector1B = cursor
              .getColumnSelectorFactory()
              .makeDimensionSelector(new DefaultDimensionSpec("billy", "billy"));
          //index gets more rows at this point, while other thread is iterating over the cursor
          try {
            index.add(new MapBasedInputRow(timestamp, Collections.singletonList("billy"), ImmutableMap.of("billy", "v2")));
            index.add(new MapBasedInputRow(timestamp, Collections.singletonList("billy2"), ImmutableMap.of("billy2", "v3")));
          }
          catch (Exception ex) {
            throw new RuntimeException(ex);
          }

          DimensionSelector dimSelector1C = cursor
              .getColumnSelectorFactory()
              .makeDimensionSelector(new DefaultDimensionSpec("billy", "billy"));

          DimensionSelector dimSelector2D = cursor
              .getColumnSelectorFactory()
              .makeDimensionSelector(new DefaultDimensionSpec("billy2", "billy2"));
          //index gets more rows at this point, while other thread is iterating over the cursor
          try {
            index.add(new MapBasedInputRow(timestamp, Collections.singletonList("billy"), ImmutableMap.of("billy", "v3")));
            index.add(new MapBasedInputRow(timestamp, Collections.singletonList("billy3"), ImmutableMap.of("billy3", "")));
          }
          catch (Exception ex) {
            throw new RuntimeException(ex);
          }

          DimensionSelector dimSelector3E = cursor
              .getColumnSelectorFactory()
              .makeDimensionSelector(new DefaultDimensionSpec("billy3", "billy3"));

          int rowNumInCursor = 0;
          // and then, cursoring continues in the other thread
          while (!cursor.isDone()) {
            IndexedInts rowA = dimSelector1A.getRow();
            rowA.forEach(i -> Assert.assertTrue(i < cardinalityA));
            IndexedInts rowB = dimSelector1B.getRow();
            rowB.forEach(i -> Assert.assertTrue(i < cardinalityA));
            IndexedInts rowC = dimSelector1C.getRow();
            rowC.forEach(i -> Assert.assertTrue(i < cardinalityA));
            IndexedInts rowD = dimSelector2D.getRow();
            // no null id, so should get empty dims array
            Assert.assertEquals(0, rowD.size());
            IndexedInts rowE = dimSelector3E.getRow();
            if (NullHandling.replaceWithDefault()) {
              Assert.assertEquals(1, rowE.size());
              // the null id
              Assert.assertEquals(0, rowE.get(0));
            } else {
              Assert.assertEquals(0, rowE.size());
            }
            cursor.advance();
            rowNumInCursor++;
          }
          Assert.assertEquals(2, rowNumInCursor);
          assertCursorsNotEmpty.incrementAndGet();

          return null;
        })
        .toList();
    Assert.assertEquals(1, assertCursorsNotEmpty.get());
  }
}
