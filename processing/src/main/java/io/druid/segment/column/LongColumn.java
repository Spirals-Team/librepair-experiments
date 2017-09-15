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

package io.druid.segment.column;

import io.druid.collections.bitmap.ImmutableBitmap;
import io.druid.segment.data.CompressedLongsIndexedSupplier;

/**
 */
public class LongColumn extends AbstractColumn
{
  public static final int ROW_SIZE = 1;

  private static final ColumnCapabilitiesImpl CAPABILITIES = new ColumnCapabilitiesImpl()
      .setType(ValueType.LONG);

  private static final ColumnCapabilitiesImpl CAPABILITIES_WITH_NULL = new ColumnCapabilitiesImpl()
      .setType(ValueType.LONG);

  private final CompressedLongsIndexedSupplier column;
  private final ImmutableBitmap nullValueBitmap;

  public LongColumn(CompressedLongsIndexedSupplier column, ImmutableBitmap nullValueBitmap)
  {
    this.column = column;
    this.nullValueBitmap = nullValueBitmap;
  }

  @Override
  public ColumnCapabilities getCapabilities()
  {
    return nullValueBitmap.isEmpty() ? CAPABILITIES : CAPABILITIES_WITH_NULL;
  }

  @Override
  public int getLength()
  {
    return column.size();
  }

  @Override
  public GenericColumn getGenericColumn()
  {
    return new IndexedLongsGenericColumn(column.get(), nullValueBitmap);
  }

}
