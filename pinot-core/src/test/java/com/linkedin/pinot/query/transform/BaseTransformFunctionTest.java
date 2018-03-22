/**
 * Copyright (C) 2014-2016 LinkedIn Corp. (pinot-core@linkedin.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.linkedin.pinot.query.transform;

import com.linkedin.pinot.common.data.DimensionFieldSpec;
import com.linkedin.pinot.common.data.FieldSpec;
import com.linkedin.pinot.common.data.Schema;
import com.linkedin.pinot.common.data.TimeFieldSpec;
import com.linkedin.pinot.common.segment.ReadMode;
import com.linkedin.pinot.core.common.DataSource;
import com.linkedin.pinot.core.data.GenericRow;
import com.linkedin.pinot.core.data.readers.GenericRowRecordReader;
import com.linkedin.pinot.core.indexsegment.IndexSegment;
import com.linkedin.pinot.core.indexsegment.generator.SegmentGeneratorConfig;
import com.linkedin.pinot.core.operator.DocIdSetOperator;
import com.linkedin.pinot.core.operator.ProjectionOperator;
import com.linkedin.pinot.core.operator.blocks.ProjectionBlock;
import com.linkedin.pinot.core.operator.filter.MatchEntireSegmentOperator;
import com.linkedin.pinot.core.plan.DocIdSetPlanNode;
import com.linkedin.pinot.core.segment.creator.impl.SegmentIndexCreationDriverImpl;
import com.linkedin.pinot.core.segment.index.loader.Loaders;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


public abstract class BaseTransformFunctionTest {
  private static final String SEGMENT_NAME = "testSegment";
  private static final String INDEX_DIR_PATH = FileUtils.getTempDirectoryPath() + File.separator + SEGMENT_NAME;
  private static final Random RANDOM = new Random();

  protected static final int NUM_ROWS = 1000;
  protected static final int MAX_VALUE = 10;
  protected static final int MAX_NUM_MULTI_VALUES = 5;
  protected static final String LONG_SV_COLUMN = "longSV";
  protected static final String DOUBLE_SV_COLUMN = "doubleSV";
  protected static final String INT_MV_COLUMN = "intMV";
  protected static final String TIME_COLUMN = "time";

  protected final long[] _longSVValues = new long[NUM_ROWS];
  protected final double[] _doubleSVValues = new double[NUM_ROWS];
  protected final int[][] _intMVValues = new int[NUM_ROWS][];
  protected final long[] _timeValues = new long[NUM_ROWS];

  protected Map<String, DataSource> _dataSourceMap;
  protected ProjectionBlock _projectionBlock;

  @BeforeClass
  public void setUp() throws Exception {
    FileUtils.deleteQuietly(new File(INDEX_DIR_PATH));

    long currentTimeMs = System.currentTimeMillis();
    for (int i = 0; i < NUM_ROWS; i++) {
      _longSVValues[i] = 1 + RANDOM.nextInt(MAX_VALUE);
      _doubleSVValues[i] = 1 + RANDOM.nextInt(MAX_VALUE);
      int numValues = 1 + RANDOM.nextInt(MAX_NUM_MULTI_VALUES);
      _intMVValues[i] = new int[numValues];
      for (int j = 0; j < numValues; j++) {
        _intMVValues[i][j] = 1 + RANDOM.nextInt(MAX_VALUE);
      }
      // Time in the past year
      _timeValues[i] = currentTimeMs - RANDOM.nextInt(365 * 24 * 3600) * 1000L;
    }

    List<GenericRow> rows = new ArrayList<>(NUM_ROWS);
    for (int i = 0; i < NUM_ROWS; i++) {
      Map<String, Object> map = new HashMap<>();
      map.put(LONG_SV_COLUMN, _longSVValues[i]);
      map.put(DOUBLE_SV_COLUMN, _doubleSVValues[i]);
      map.put(INT_MV_COLUMN, ArrayUtils.toObject(_intMVValues[i]));
      map.put(TIME_COLUMN, _timeValues[i]);
      GenericRow row = new GenericRow();
      row.init(map);
      rows.add(row);
    }

    Schema schema = new Schema();
    schema.addField(new DimensionFieldSpec(LONG_SV_COLUMN, FieldSpec.DataType.LONG, true));
    schema.addField(new DimensionFieldSpec(DOUBLE_SV_COLUMN, FieldSpec.DataType.DOUBLE, true));
    schema.addField(new DimensionFieldSpec(INT_MV_COLUMN, FieldSpec.DataType.INT, false));
    schema.addField(new TimeFieldSpec(TIME_COLUMN, FieldSpec.DataType.LONG, TimeUnit.MILLISECONDS));

    SegmentGeneratorConfig config = new SegmentGeneratorConfig(schema);
    config.setOutDir(INDEX_DIR_PATH);
    config.setSegmentName(SEGMENT_NAME);
    SegmentIndexCreationDriverImpl driver = new SegmentIndexCreationDriverImpl();
    driver.init(config, new GenericRowRecordReader(rows, schema));
    driver.build();

    IndexSegment indexSegment = Loaders.IndexSegment.load(new File(INDEX_DIR_PATH, SEGMENT_NAME), ReadMode.heap);
    Set<String> columnNames = indexSegment.getColumnNames();
    _dataSourceMap = new HashMap<>(columnNames.size());
    for (String columnName : columnNames) {
      _dataSourceMap.put(columnName, indexSegment.getDataSource(columnName));
    }

    _projectionBlock = new ProjectionOperator(_dataSourceMap,
        new DocIdSetOperator(new MatchEntireSegmentOperator(NUM_ROWS), DocIdSetPlanNode.MAX_DOC_PER_CALL)).nextBlock();
  }

  @AfterClass
  public void tearDown() {
    FileUtils.deleteQuietly(new File(INDEX_DIR_PATH));
  }
}
