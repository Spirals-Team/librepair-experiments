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

package io.druid.query.extraction;

import io.druid.common.config.NullHandling;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class LowerExtractionFnTest
{
  ExtractionFn extractionFn = new LowerExtractionFn(null);
  @Test
  public void testApply()
  {
    Assert.assertEquals("lower 1 string", extractionFn.apply("lOwER 1 String"));
    Assert.assertEquals(NullHandling.useDefaultValuesForNull() ? null : "", extractionFn.apply(""));
    Assert.assertEquals(null, extractionFn.apply(null));
    Assert.assertEquals(null, extractionFn.apply((Object) null));
    Assert.assertEquals("1", extractionFn.apply(1));
  }

  @Test
  public void testGetCacheKey()
  {
    Assert.assertArrayEquals(extractionFn.getCacheKey(), extractionFn.getCacheKey());
    Assert.assertFalse(Arrays.equals(extractionFn.getCacheKey(), new UpperExtractionFn(null).getCacheKey()));
  }
}
