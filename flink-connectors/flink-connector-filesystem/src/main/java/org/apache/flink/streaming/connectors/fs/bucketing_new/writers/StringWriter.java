/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.streaming.connectors.fs.bucketing_new.writers;

import org.apache.flink.core.fs.FSDataOutputStream;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * A {@link Writer} that uses {@code toString()} on the input elements and writes them to
 * the output bucket file separated by newline.
 *
 * @param <T> The type of the elements that are being written by the sink.
 */
public class StringWriter<T> implements Writer<T> {

	private static final long serialVersionUID = -6865107843734614452L;

	private String charsetName;

	private transient Charset charset;

	/**
	 * Creates a new {@code StringWriter} that uses {@code "UTF-8"} charset to convert
	 * strings to bytes.
	 */
	public StringWriter() {
		this("UTF-8");
	}

	/**
	 * Creates a new {@code StringWriter} that uses the given charset to convert
	 * strings to bytes.
	 *
	 * @param charsetName Name of the charset to be used, must be valid input for {@code Charset.forName(charsetName)}
	 */
	public StringWriter(String charsetName) {
		this.charsetName = charsetName;
	}

	@Override
	public void write(T element, FSDataOutputStream stream) throws IOException {
		if (charset == null) {
			charset = Charset.forName(charsetName);
		}

		stream.write(element.toString().getBytes(charset));
		stream.write('\n');
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), charsetName);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other == null) {
			return false;
		}
		if (getClass() != other.getClass()) {
			return false;
		}
		StringWriter<T> writer = (StringWriter<T>) other;
		// field comparison
		return Objects.equals(charsetName, writer.charsetName)
			&& super.equals(other);
	}
}
