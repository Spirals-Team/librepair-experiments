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

package io.druid.query.dimension;


import io.druid.query.extraction.ExtractionFn;
import io.druid.segment.DimensionSelector;
import io.druid.segment.column.ValueType;

public class PushDownQueryDimensionSpec implements DimensionSpec
{
  private final DimensionSpec delegate;

  public PushDownQueryDimensionSpec(DimensionSpec delegate)
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
