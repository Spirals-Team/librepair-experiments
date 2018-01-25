/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.core.memory;

import java.io.IOException;
import java.io.InputStream;

import static org.apache.flink.util.Preconditions.checkNotNull;
import static org.apache.flink.util.Preconditions.checkState;

/**
 * An un-synchronized stream that connects a prepend byte array buffer to another wrapped {@link InputStream}.
 * The prepend buffer will be fully read before reading the wrapped input stream.
 */
public class ByteArrayPrefixInputStream extends InputStream {

	private final byte[] buffer;
	private final InputStream wrappedInputStream;

	/**
	 * Current position in the prepend buffer.
	 * If the buffer is already fully read, the position will be equal to the buffer length.
	 */
	private int position;

	/**
	 *
	 */
	private int mark;

	/**
	 * 	number of bytes to read from the input buffer array
	 */
	private int count;

	public ByteArrayPrefixInputStream(byte[] bufferedBytes, InputStream inputStream) {
		this(bufferedBytes, 0, bufferedBytes.length, inputStream);
	}

	public ByteArrayPrefixInputStream(byte[] bufferedBytes, int offset, int length, InputStream inputStream) {
		this.buffer = bufferedBytes;
		this.wrappedInputStream = inputStream;
		this.position = offset;
		this.count = Math.min(offset + length, buffer.length);
		this.mark = offset;
	}

	@Override
	public int read() throws IOException {
		if (position < count) {

		}
		return (position < count) ? (0xFF & buffer[position++]) : wrappedInputStream.read();
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		checkNotNull(b);

		if (off < 0 || len < 0 || len > b.length - off) {
			throw new IndexOutOfBoundsException();
		} else if (len == 0) {
			return 0;
		}

		int numAvailableBufferedBytes = count - position;

		if (numAvailableBufferedBytes <= 0) {
			return wrappedInputStream.read(b, off, len);
		} else {
			if (len > numAvailableBufferedBytes) {
				System.arraycopy(buffer, position, b, off, numAvailableBufferedBytes);
				position += numAvailableBufferedBytes;

				int numBytesFromInputStream = wrappedInputStream.read(
					b, off + numAvailableBufferedBytes, len - numAvailableBufferedBytes);

				return numBytesFromInputStream < 0
					? numAvailableBufferedBytes
					: numAvailableBufferedBytes + numBytesFromInputStream;
			} else {
				System.arraycopy(buffer, position, b, off, len);
				position += len;
				return len;
			}
		}
	}

	@Override
	public long skip(long n) throws IOException {
		long numAvailableBufferedBytes = count - position;

		if (numAvailableBufferedBytes <= 0) {
			return wrappedInputStream.skip(n);
		} else {
			if (n > numAvailableBufferedBytes) {
				position += numAvailableBufferedBytes;

				return numAvailableBufferedBytes + wrappedInputStream.skip(n - numAvailableBufferedBytes);
			} else {
				long skipped = n < 0 ? 0 : n;
				position += skipped;

				return skipped;
			}
		}
	}

	@Override
	public int available() throws IOException {
		int numAvailableBufferedBytes = count - position;

		return numAvailableBufferedBytes < 0
			? wrappedInputStream.available()
			: wrappedInputStream.available() + numAvailableBufferedBytes;
	}

	@Override
	public boolean markSupported() {
		return wrappedInputStream.markSupported();
	}

	@Override
	public void mark(int readlimit) {
		checkState(markSupported());

		if (count - position <= 0) {
			wrappedInputStream.mark(readlimit);
			mark = -1;
		} else {
			mark = position;
		}
	}

	@Override
	public void reset() throws IOException {
		checkState(markSupported());

		wrappedInputStream.reset();
		if (mark != -1) {
			position = mark;
		}
	}

	@Override
	public void close() throws IOException {
		wrappedInputStream.close();
	}
}
