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

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 *
 */
public class ByteArrayPrefixInputStreamTest {

	@Test
	public void test() throws IOException {
		final byte[] bufferedBytes = new byte[] {12, 52, 127, 121};

		final byte[] inputStreamBytes = new byte[] {51, 45, 89, 45, 100, 96};

		final ByteArrayPrefixInputStream test = new ByteArrayPrefixInputStream(bufferedBytes, new ByteArrayInputStream(inputStreamBytes));

		System.out.println(test.read());
		System.out.println(test.read());
		System.out.println(test.read());
		System.out.println(test.read());
		System.out.println(test.read());
		System.out.println(test.read());
		System.out.println(test.read());
		System.out.println(test.read());
		System.out.println(test.read());
		System.out.println(test.read());
		System.out.println(test.read());

		test.reset();

		byte[] readBytes = new byte[1];
		byte[] readBytes2 = new byte[2];
		byte[] readBytes3 = new byte[4];

		System.out.println(test.read(readBytes));
		System.out.println(test.read(readBytes2));

		test.mark(-1);
		System.out.println(test.read(readBytes3));

		for (byte b : readBytes) {
			System.out.println((int) b);
		}

		for (byte b : readBytes2) {
			System.out.println((int) b);
		}

		for (byte b : readBytes3) {
			System.out.println((int) b);
		}

		test.reset();
		readBytes = new byte[1];
		readBytes2 = new byte[2];
		readBytes3 = new byte[5];

		System.out.println(test.read(readBytes));
		System.out.println(test.read(readBytes2));

		for (byte b : readBytes) {
			System.out.println((int) b);
		}

		for (byte b : readBytes2) {
			System.out.println((int) b);
		}

		test.mark(-1);
		System.out.println(test.read(readBytes3));

		for (byte b : readBytes3) {
			System.out.println((int) b);
		}

		test.reset();
		readBytes3 = new byte[3];
		System.out.println(test.read(readBytes3));

		for (byte b : readBytes3) {
			System.out.println((int) b);
		}
	}

}
