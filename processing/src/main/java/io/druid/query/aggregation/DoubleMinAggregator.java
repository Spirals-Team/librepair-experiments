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

package io.druid.query.aggregation;

import io.druid.segment.BaseDoubleColumnValueSelector;

/**
 */
public class DoubleMinAggregator implements Aggregator
{
  static double combineValues(Object lhs, Object rhs)
  {
    return Math.min(((Number) lhs).doubleValue(), ((Number) rhs).doubleValue());
  }

  private final BaseDoubleColumnValueSelector selector;

  private double min;

  public DoubleMinAggregator(BaseDoubleColumnValueSelector selector)
  {
    this.selector = selector;

    reset();
  }

  @Override
  public void aggregate()
  {
    min = Math.min(min, selector.getDouble());
  }

  @Override
  public void reset()
  {
    min = Double.POSITIVE_INFINITY;
  }

  @Override
  public Object get()
  {
    return min;
  }

  @Override
  public float getFloat()
  {
    return (float) min;
  }

  @Override
  public long getLong()
  {
    return (long) min;
  }

  @Override
  public double getDouble()
  {
    return min;
  }

  @Override
  public Aggregator clone()
  {
    return new DoubleMinAggregator(selector);
  }

  @Override
  public void close()
  {
    // no resources to cleanup
  }
}
