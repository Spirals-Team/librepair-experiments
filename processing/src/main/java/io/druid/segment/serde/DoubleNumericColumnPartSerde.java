/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.druid.segment.serde;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Supplier;
import io.druid.segment.IndexIO;
import io.druid.segment.column.ColumnBuilder;
import io.druid.segment.column.ColumnConfig;
import io.druid.segment.column.ValueType;
import io.druid.segment.data.ColumnarDoubles;
import io.druid.segment.data.CompressedColumnarDoublesSuppliers;

import javax.annotation.Nullable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DoubleNumericColumnPartSerde implements ColumnPartSerde
{
  @JsonCreator
  public static DoubleNumericColumnPartSerde getDoubleGenericColumnPartSerde(
      @JsonProperty("byteOrder") ByteOrder byteOrder
  )
  {
    return new DoubleNumericColumnPartSerde(byteOrder, null);
  }

  private final ByteOrder byteOrder;
  @Nullable
  private final Serializer serializer;

  private DoubleNumericColumnPartSerde(ByteOrder byteOrder, @Nullable Serializer serializer)
  {
    this.byteOrder = byteOrder;
    this.serializer = serializer;
  }

  @JsonProperty
  public ByteOrder getByteOrder()
  {
    return byteOrder;
  }

  public static SerializerBuilder serializerBuilder()
  {
    return new SerializerBuilder();
  }

  public static class SerializerBuilder
  {
    private ByteOrder byteOrder = null;
    private Serializer delegate = null;

    public SerializerBuilder withByteOrder(final ByteOrder byteOrder)
    {
      this.byteOrder = byteOrder;
      return this;
    }

    public SerializerBuilder withDelegate(final Serializer delegate)
    {
      this.delegate = delegate;
      return this;
    }

    public DoubleNumericColumnPartSerde build()
    {
      return new DoubleNumericColumnPartSerde(byteOrder, delegate);
    }
  }

  @Nullable
  @Override
  public Serializer getSerializer()
  {
    return serializer;
  }

  @Override
  public Deserializer getDeserializer()
  {
    return new Deserializer()
    {
      @Override
      public void read(ByteBuffer buffer, ColumnBuilder builder, ColumnConfig columnConfig)
      {
        final Supplier<ColumnarDoubles> column = CompressedColumnarDoublesSuppliers.fromByteBuffer(
            buffer,
            byteOrder
        );
        DoubleNumericColumnSupplier columnSupplier = new DoubleNumericColumnSupplier(
            column,
            IndexIO.LEGACY_FACTORY.getBitmapFactory().makeEmptyImmutableBitmap()
        );
        builder.setType(ValueType.DOUBLE)
               .setHasMultipleValues(false)
               .setNumericColumnSupplier(columnSupplier);

      }
    };
  }
}
