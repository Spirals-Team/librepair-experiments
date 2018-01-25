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

import org.apache.flink.core.memory.ByteArrayPrefixInputStream;
import org.apache.flink.core.memory.DataInputView;
import org.apache.flink.core.memory.DataInputViewStreamWrapper;
import org.apache.flink.core.memory.DataOutputView;
import org.apache.flink.core.memory.InputStreamViewWrapper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 */
public abstract class PostVersionedIOReadableWritable extends VersionedIOReadableWritable {

	/** CANNOT CHANGE! */
	private static final byte[] VERSIONED_IDENTIFIER = new byte[] {15, 51, 123, 97};

	protected abstract void read(DataInputView in, boolean wasVersioned) throws IOException;

	@Override
	public void write(DataOutputView out) throws IOException {
		out.write(VERSIONED_IDENTIFIER);
		super.write(out);
	}

	@Override
	public final void read(DataInputView in) throws IOException {
		InputStreamViewWrapper inWrapper = new InputStreamViewWrapper(in);

		byte[] tmp = new byte[VERSIONED_IDENTIFIER.length];
		inWrapper.read(tmp);

		if (Arrays.equals(tmp, VERSIONED_IDENTIFIER)) {
			super.read(in);
			read(in, true);
		} else {
			read(new DataInputViewStreamWrapper(new ByteArrayPrefixInputStream(tmp, inWrapper)), false);
		}
	}
}
