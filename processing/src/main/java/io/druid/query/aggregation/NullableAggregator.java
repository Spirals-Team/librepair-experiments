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

import io.druid.segment.ColumnValueSelector;

public class NullableAggregator implements Aggregator
{
  private final Aggregator delegate;
  private final ColumnValueSelector selector;
  private boolean isNull = true;

  public NullableAggregator(Aggregator delegate, ColumnValueSelector selector)
  {
    this.delegate = delegate;
    this.selector = selector;
  }

  @Override
  public void aggregate()
  {
    if (isNull && !selector.isNull()) {
      isNull = false;
    }
    delegate.aggregate();
  }

  @Override
  public void reset()
  {
    isNull = true;
    delegate.reset();
  }

  @Override
  public Object get()
  {
    return delegate.get();
  }

  @Override
  public float getFloat()
  {
    return delegate.getFloat();
  }

  @Override
  public long getLong()
  {
    return delegate.getLong();
  }

  @Override
  public double getDouble()
  {
    return delegate.getDouble();
  }

  @Override
  public boolean isNull()
  {
    return isNull;
  }

  @Override
  public void close()
  {
    delegate.close();
  }
}
