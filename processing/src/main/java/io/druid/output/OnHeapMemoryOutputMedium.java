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

package io.druid.output;

import com.google.common.annotations.VisibleForTesting;
import io.druid.java.util.common.io.Closer;

import java.io.IOException;

@VisibleForTesting
final class OnHeapMemoryOutputMedium implements OutputMedium
{
  private final Closer closer = Closer.create();

  @Override
  public OutputBytes makeOutputBytes() throws IOException
  {
    return new HeapByteBufferOutputBytes();
  }

  @Override
  public Closer getCloser()
  {
    return closer;
  }

  @Override
  public void close() throws IOException
  {
    closer.close();
  }
}
