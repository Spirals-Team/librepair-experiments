/*
 * #%L
 * Image encoding and decoding routines.
 * %%
 * Copyright (C) 2005 - 2017 Open Microscopy Environment:
 *   - Board of Regents of the University of Wisconsin-Madison
 *   - Glencoe Software, Inc.
 *   - University of Dundee
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package ome.codecs;

import java.io.IOException;

import loci.common.RandomAccessInputStream;
import ome.codecs.CodecException;

/**
 * A codec which just returns the exact data it was given, performing no
 * compression or decompression.
 */
public class PassthroughCodec extends BaseCodec {

  /* (non-Javadoc)
   * @see ome.codecs.BaseCodec#decompress(byte[], ome.codecs.CodecOptions)
   */
  @Override
  public byte[] decompress(byte[] data, CodecOptions options)
      throws CodecException {
    return data;
  }

  /* (non-Javadoc)
   * @see ome.codecs.BaseCodec#decompress(loci.common.RandomAccessInputStream, ome.codecs.CodecOptions)
   */
  @Override
  public byte[] decompress(RandomAccessInputStream in, CodecOptions options)
      throws CodecException, IOException {
    throw new RuntimeException("Not implemented.");
  }

  /* (non-Javadoc)
   * @see ome.codecs.Codec#compress(byte[], ome.codecs.CodecOptions)
   */
  @Override
  public byte[] compress(byte[] data, CodecOptions options)
      throws CodecException {
    return data;
  }

}
