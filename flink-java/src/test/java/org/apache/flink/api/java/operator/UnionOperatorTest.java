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

package org.apache.flink.api.java.operator;

import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.api.java.typeutils.TupleTypeInfo;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for {@link DataSet#union(DataSet)}.
 */
public class UnionOperatorTest {

	// TUPLE DATA
	private static final List<Tuple5<Integer, Long, String, Long, Integer>> emptyTupleData =
		new ArrayList<>();

	private final TupleTypeInfo<Tuple5<Integer, Long, String, Long, Integer>> tupleTypeInfo = new
		TupleTypeInfo<>(
		BasicTypeInfo.INT_TYPE_INFO,
		BasicTypeInfo.LONG_TYPE_INFO,
		BasicTypeInfo.STRING_TYPE_INFO
	);

	private static List<CustomType> customTypeData = new ArrayList<>();

	@BeforeClass
	public static void insertCustomData() {
		customTypeData.add(new CustomType());
	}

	@Test
	public void testUnion() {
		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
		DataSet<Tuple5<Integer, Long, String, Long, Integer>> ds1 =
				env.fromCollection(emptyTupleData, tupleTypeInfo);
		DataSet<Tuple5<Integer, Long, String, Long, Integer>> ds2 =
				env.fromCollection(emptyTupleData, tupleTypeInfo);

		// should work
		try {
			ds1.union(ds2);
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testSetUnionParallelism() {
		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
		DataSet<Tuple5<Integer, Long, String, Long, Integer>> ds1 = env.fromCollection
				(emptyTupleData, tupleTypeInfo);
		DataSet<Tuple5<Integer, Long, String, Long, Integer>> ds2 = env.fromCollection
				(emptyTupleData, tupleTypeInfo);

		// Setting the parallelism for Union is forbidden
		ds1.union(ds2).setParallelism(1);
	}

	/*
	 * ####################################################################
	 */

	private static class CustomType implements Serializable {

		private static final long serialVersionUID = 1L;

		public int myInt;
		public long myLong;
		public String myString;

		public CustomType() {
		}

		public CustomType(int i, long l, String s) {
			myInt = i;
			myLong = l;
			myString = s;
		}

		@Override
		public String toString() {
			return myInt + "," + myLong + "," + myString;
		}
	}

}
