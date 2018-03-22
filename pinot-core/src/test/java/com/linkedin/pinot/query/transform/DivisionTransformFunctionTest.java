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

import com.linkedin.pinot.common.request.transform.TransformExpressionTree;
import com.linkedin.pinot.core.operator.transform.function.DivisionTransformFunction;
import com.linkedin.pinot.core.operator.transform.function.TransformFunction;
import com.linkedin.pinot.core.operator.transform.function.TransformFunctionFactory;
import org.testng.Assert;
import org.testng.annotations.Test;


public class DivisionTransformFunctionTest extends BaseTransformFunctionTest {

  @Test
  public void testDivisionTransformFunction() {
    TransformExpressionTree expression =
        TransformExpressionTree.compileToExpressionTree(String.format("div(%s,12.34)", LONG_SV_COLUMN));
    TransformFunction transformFunction = TransformFunctionFactory.get(expression, _dataSourceMap);
    Assert.assertTrue(transformFunction instanceof DivisionTransformFunction);
    int[] intValues = transformFunction.transformToIntValuesSV(_projectionBlock);
    long[] longValues = transformFunction.transformToLongValuesSV(_projectionBlock);
    float[] floatValues = transformFunction.transformToFloatValuesSV(_projectionBlock);
    double[] doubleValues = transformFunction.transformToDoubleValuesSV(_projectionBlock);
    String[] stringValues = transformFunction.transformToStringValuesSV(_projectionBlock);
    for (int i = 0; i < NUM_ROWS; i++) {
      double expected = _longSVValues[i] / 12.34;
      Assert.assertEquals(intValues[i], (int) expected);
      Assert.assertEquals(longValues[i], (long) expected);
      Assert.assertEquals(floatValues[i], (float) expected);
      Assert.assertEquals(doubleValues[i], expected);
      Assert.assertEquals(stringValues[i], Double.toString(expected));
    }

    expression = TransformExpressionTree.compileToExpressionTree(
        String.format("div(div(12,%s),div(div(%s,%s),0.34))", DOUBLE_SV_COLUMN, LONG_SV_COLUMN, DOUBLE_SV_COLUMN));
    transformFunction = TransformFunctionFactory.get(expression, _dataSourceMap);
    Assert.assertTrue(transformFunction instanceof DivisionTransformFunction);
    intValues = transformFunction.transformToIntValuesSV(_projectionBlock);
    longValues = transformFunction.transformToLongValuesSV(_projectionBlock);
    floatValues = transformFunction.transformToFloatValuesSV(_projectionBlock);
    doubleValues = transformFunction.transformToDoubleValuesSV(_projectionBlock);
    stringValues = transformFunction.transformToStringValuesSV(_projectionBlock);
    for (int i = 0; i < NUM_ROWS; i++) {
      double expected = (12 / _doubleSVValues[i]) / ((_longSVValues[i] / _doubleSVValues[i]) / 0.34);
      Assert.assertEquals(intValues[i], (int) expected);
      Assert.assertEquals(longValues[i], (long) expected);
      Assert.assertEquals(floatValues[i], (float) expected);
      Assert.assertEquals(doubleValues[i], expected);
      Assert.assertEquals(stringValues[i], Double.toString(expected));
    }
  }
}
