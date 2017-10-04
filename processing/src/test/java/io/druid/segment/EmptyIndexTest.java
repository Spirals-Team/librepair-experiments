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

package io.druid.segment;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import io.druid.collections.bitmap.ConciseBitmapFactory;
import io.druid.java.util.common.Intervals;
import io.druid.output.OffHeapMemoryOutputMediumFactory;
import io.druid.output.OutputMediumFactory;
import io.druid.output.TmpFileOutputMediumFactory;
import io.druid.query.aggregation.AggregatorFactory;
import io.druid.segment.column.Column;
import io.druid.segment.incremental.IncrementalIndex;
import io.druid.segment.incremental.IncrementalIndexAdapter;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

@RunWith(Parameterized.class)
public class EmptyIndexTest
{

  @Parameterized.Parameters
  public static Collection<?> constructorFeeder() throws IOException
  {
    return ImmutableList.of(
        new Object[] {TmpFileOutputMediumFactory.instance()},
        new Object[] {OffHeapMemoryOutputMediumFactory.instance()}
    );
  }

  private final OutputMediumFactory outputMediumFactory;

  public EmptyIndexTest(OutputMediumFactory outputMediumFactory)
  {
    this.outputMediumFactory = outputMediumFactory;
  }

  @Test
  public void testEmptyIndex() throws Exception
  {
    File tmpDir = File.createTempFile("emptyIndex", "");
    if (!tmpDir.delete()) {
      throw new IllegalStateException("tmp delete failed");
    }
    if (!tmpDir.mkdir()) {
      throw new IllegalStateException("tmp mkdir failed");
    }

    try {
      IncrementalIndex emptyIndex = new IncrementalIndex.Builder()
          .setSimpleTestingIndexSchema(/* empty */)
          .setMaxRowCount(1000)
          .buildOnheap();

      IncrementalIndexAdapter emptyIndexAdapter = new IncrementalIndexAdapter(
          Intervals.of("2012-08-01/P3D"),
          emptyIndex,
          new ConciseBitmapFactory()
      );
      TestHelper.getTestIndexMergerV9(outputMediumFactory).merge(
          Lists.<IndexableAdapter>newArrayList(emptyIndexAdapter),
          true,
          new AggregatorFactory[0],
          tmpDir,
          new IndexSpec()
      );

      QueryableIndex emptyQueryableIndex = TestHelper.getTestIndexIO(outputMediumFactory).loadIndex(tmpDir);

      Assert.assertEquals("getDimensionNames", 0, Iterables.size(emptyQueryableIndex.getAvailableDimensions()));
      Assert.assertEquals("getMetricNames", 0, Iterables.size(emptyQueryableIndex.getColumnNames()));
      Assert.assertEquals("getDataInterval", Intervals.of("2012-08-01/P3D"), emptyQueryableIndex.getDataInterval());
      Assert.assertEquals(
          "getReadOnlyTimestamps",
          0,
          emptyQueryableIndex.getColumn(Column.TIME_COLUMN_NAME).getLength()
      );
    }
    finally {
      FileUtils.deleteDirectory(tmpDir);
    }
  }
}
