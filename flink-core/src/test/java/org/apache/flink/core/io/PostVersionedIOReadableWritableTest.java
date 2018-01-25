/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.core.io;

import org.apache.flink.core.memory.ByteArrayInputStreamWithPos;
import org.apache.flink.core.memory.ByteArrayOutputStreamWithPos;
import org.apache.flink.core.memory.DataInputView;
import org.apache.flink.core.memory.DataInputViewStreamWrapper;
import org.apache.flink.core.memory.DataOutputView;
import org.apache.flink.core.memory.DataOutputViewStreamWrapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 *
 */
public class PostVersionedIOReadableWritableTest {

	@Test
	public void testReadNonVersioned() throws IOException {
		int preVersionedPayload = 563;

		TestNonVersionedReadableWritable nonVersionedReadableWritable = new TestNonVersionedReadableWritable(preVersionedPayload);

		byte[] serialized;
		try (ByteArrayOutputStreamWithPos out = new ByteArrayOutputStreamWithPos()) {
			nonVersionedReadableWritable.write(new DataOutputViewStreamWrapper(out));
			serialized = out.toByteArray();
		}

		TestReadableWritable versionedReadableWritable = new TestReadableWritable();
		try(ByteArrayInputStreamWithPos in = new ByteArrayInputStreamWithPos(serialized)) {
			versionedReadableWritable.read(new DataInputViewStreamWrapper(in));
		}

		Assert.assertEquals(String.valueOf(preVersionedPayload), versionedReadableWritable.getData());
	}

	static class TestReadableWritable extends PostVersionedIOReadableWritable {

		private static final int VERSION = 1;
		private String data;

		@Override
		public int getVersion() {
			return VERSION;
		}

		@Override
		public void write(DataOutputView out) throws IOException {
			super.write(out);
			out.writeUTF(data);
		}

		@Override
		protected void read(DataInputView in, boolean wasVersioned) throws IOException {
			if (wasVersioned) {
				this.data = in.readUTF();
			} else {
				this.data = String.valueOf(in.readInt());
			}
		}

		public String getData() {
			return data;
		}
	}

	static class TestNonVersionedReadableWritable implements IOReadableWritable {

		private int data;

		TestNonVersionedReadableWritable(int data) {
			this.data = data;
		}

		@Override
		public void write(DataOutputView out) throws IOException {
			out.writeInt(data);
		}

		@Override
		public void read(DataInputView in) throws IOException {
			this.data = in.readInt();
		}
	}

}
