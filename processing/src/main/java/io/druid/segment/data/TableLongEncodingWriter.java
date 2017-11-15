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

package io.druid.segment.data;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import io.druid.common.utils.SerializerUtils;
import io.druid.java.util.common.IAE;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.LongList;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class TableLongEncodingWriter implements CompressionFactory.LongEncodingWriter
{

  private final Long2IntMap table;
  private final LongList valueAddedInOrder;
  private final int bitsPerValue;
  private VSizeLongSerde.LongSerializer serializer;

  public TableLongEncodingWriter(Long2IntMap table, LongList valueAddedInOrder)
  {
    if (table.size() > CompressionFactory.MAX_TABLE_SIZE) {
      throw new IAE("Invalid table size[%s]", table.size());
    }
    this.table = table;
    this.valueAddedInOrder = valueAddedInOrder;
    this.bitsPerValue = VSizeLongSerde.getBitsForMax(table.size());
  }

  @Override
  public void setBuffer(ByteBuffer buffer)
  {
    serializer = VSizeLongSerde.getSerializer(bitsPerValue, buffer, buffer.position());
  }

  @Override
  public void setOutputStream(OutputStream output)
  {
    serializer = VSizeLongSerde.getSerializer(bitsPerValue, output);
  }

  @Override
  public void write(long value) throws IOException
  {
    serializer.write(table.get(value));
  }

  @Override
  public void flush() throws IOException
  {
    if (serializer != null) {
      serializer.close();
    }
  }

  @Override
  public void putMeta(OutputStream metaOut, CompressedObjectStrategy.CompressionStrategy strategy) throws IOException
  {
    metaOut.write(CompressionFactory.setEncodingFlag(strategy.getId()));
    metaOut.write(CompressionFactory.LongEncodingFormat.TABLE.getId());
    metaOut.write(CompressionFactory.TABLE_ENCODING_VERSION);
    metaOut.write(Ints.toByteArray(table.size()));
    ByteBuffer helperBuffer = ByteBuffer.allocate(Longs.BYTES);
    for (int i = 0; i < valueAddedInOrder.size(); i++) {
      SerializerUtils.writeBigEndianLongToOutputStream(metaOut, valueAddedInOrder.getLong(i), helperBuffer);
    }
  }

  @Override
  public int getBlockSize(int bytesPerBlock)
  {
    return VSizeLongSerde.getNumValuesPerBlock(bitsPerValue, bytesPerBlock);
  }

  @Override
  public int getNumBytes(int values)
  {
    return VSizeLongSerde.getSerializedSize(bitsPerValue, values);
  }
}
