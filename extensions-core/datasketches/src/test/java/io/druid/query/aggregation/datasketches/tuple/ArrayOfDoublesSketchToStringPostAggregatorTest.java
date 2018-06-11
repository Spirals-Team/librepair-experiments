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

package io.druid.query.aggregation.datasketches.tuple;

import org.junit.Assert;
import org.junit.Test;

import io.druid.query.aggregation.PostAggregator;
import io.druid.query.aggregation.post.ConstantPostAggregator;

public class ArrayOfDoublesSketchToStringPostAggregatorTest
{

  @Test
  public void equalsAndHashCode()
  {
    final PostAggregator postAgg1 = new ArrayOfDoublesSketchToStringPostAggregator(
        "a",
        new ConstantPostAggregator("", 0)
    );
    @SuppressWarnings("ObjectEqualsNull")
    final boolean equalsNull = postAgg1.equals(null);
    Assert.assertFalse(equalsNull);
    @SuppressWarnings({"EqualsWithItself", "SelfEquals"})
    final boolean equalsSelf = postAgg1.equals(postAgg1); 
    Assert.assertTrue(equalsSelf);
    Assert.assertEquals(postAgg1.hashCode(), postAgg1.hashCode());

    // equals
    final PostAggregator postAgg2 = new ArrayOfDoublesSketchToStringPostAggregator(
        "a",
        new ConstantPostAggregator("", 0)
    );
    Assert.assertTrue(postAgg1.equals(postAgg2));
    Assert.assertEquals(postAgg1.hashCode(), postAgg2.hashCode());

    // same class, different field
    final PostAggregator postAgg3 = new ArrayOfDoublesSketchToStringPostAggregator(
        "a",
        new ConstantPostAggregator("", 1)
    );
    Assert.assertFalse(postAgg1.equals(postAgg3));

    // same class, different name
    final PostAggregator postAgg4 = new ArrayOfDoublesSketchToStringPostAggregator(
        "b",
        new ConstantPostAggregator("", 0)
    );
    Assert.assertFalse(postAgg1.equals(postAgg4));

    // different class, same parent, also not overriding equals and hashCode
    final PostAggregator postAgg5 = new ArrayOfDoublesSketchToNumEntriesPostAggregator(
        "a",
        new ConstantPostAggregator("", 0)
    );
    Assert.assertFalse(postAgg1.equals(postAgg5));
  }

}
