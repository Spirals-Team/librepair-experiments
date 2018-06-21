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

package org.apache.flink.api.common.typeutils.base.array;

import org.apache.flink.api.common.typeutils.SerializerTestBase;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.api.common.typeutils.base.array.FloatPrimitiveArraySerializer;
import org.apache.flink.api.common.typeutils.base.array.LongPrimitiveArraySerializer;

/**
 * A test for the {@link LongPrimitiveArraySerializer}.
 */
public class FloatPrimitiveArraySerializerTest extends SerializerTestBase<float[]> {

	@Override
	protected TypeSerializer<float[]> createSerializer() {
		return new FloatPrimitiveArraySerializer();
	}

	@Override
	protected Class<float[]> getTypeClass() {
		return float[].class;
	}
	
	@Override
	protected int getLength() {
		return -1;
	}

	@Override
	protected float[][] getTestData() {
		return new float[][] {
			new float[] {0, 1, 2, 3, -1, -2, -3, Integer.MAX_VALUE, Integer.MIN_VALUE, Float.MAX_VALUE, Float.MIN_VALUE},
			new float[] {Float.NEGATIVE_INFINITY},
			new float[] {},
			new float[] {-1, -2, 96769243, Float.NaN, Float.POSITIVE_INFINITY, 26782, Float.MIN_NORMAL, 0, 0, 0}
		};
	}
}
