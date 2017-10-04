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
import io.druid.common.utils.ByteUtils;
import io.druid.java.util.common.io.smoosh.FileSmoosher;
import io.druid.output.OutputBytes;
import io.druid.output.OutputMedium;
import io.druid.segment.serde.MetaSerdeHelper;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

/**
 * Streams integers out in the binary format described by VSizeIndexedInts
 */
public class VSizeIndexedIntsWriter extends SingleValueIndexedIntsWriter
{
  private static final byte VERSION = VSizeIndexedInts.VERSION;

  private static final MetaSerdeHelper<VSizeIndexedIntsWriter> metaSerdeHelper = MetaSerdeHelper
      .firstWriteByte((VSizeIndexedIntsWriter x) -> VERSION)
      .writeByte(x -> ByteUtils.checkedCast(x.numBytes))
      .writeInt(x -> Ints.checkedCast(x.valuesOut.size()));

  private final OutputMedium outputMedium;
  private final int numBytes;

  private final ByteBuffer helperBuffer = ByteBuffer.allocate(Ints.BYTES);
  private OutputBytes valuesOut = null;
  private boolean bufPaddingWritten = false;

  public VSizeIndexedIntsWriter(final OutputMedium outputMedium, final int maxValue)
  {
    this.outputMedium = outputMedium;
    this.numBytes = VSizeIndexedInts.getNumBytesForMax(maxValue);
  }

  @Override
  public void open() throws IOException
  {
    valuesOut = outputMedium.makeOutputBytes();
  }

  @Override
  protected void addValue(int val) throws IOException
  {
    if (bufPaddingWritten) {
      throw new IllegalStateException("written out already");
    }
    helperBuffer.putInt(0, val);
    valuesOut.write(helperBuffer.array(), Ints.BYTES - numBytes, numBytes);
  }

  @Override
  public long getSerializedSize() throws IOException
  {
    writeBufPadding();
    return metaSerdeHelper.size(this) + valuesOut.size();
  }

  @Override
  public void writeTo(WritableByteChannel channel, FileSmoosher smoosher) throws IOException
  {
    writeBufPadding();
    metaSerdeHelper.writeTo(channel, this);
    valuesOut.writeTo(channel);
  }

  private void writeBufPadding() throws IOException
  {
    if (!bufPaddingWritten) {
      byte[] bufPadding = new byte[Ints.BYTES - numBytes];
      valuesOut.write(bufPadding);
      bufPaddingWritten = true;
    }
  }
}
