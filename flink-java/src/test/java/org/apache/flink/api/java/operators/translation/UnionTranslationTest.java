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

package org.apache.flink.api.java.operators.translation;

import org.apache.flink.api.common.Plan;
import org.apache.flink.api.common.operators.GenericDataSinkBase;
import org.apache.flink.api.common.operators.Union;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.DiscardingOutputFormat;
import org.apache.flink.api.java.tuple.Tuple1;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Tests for translation of union.
 */
public class UnionTranslationTest {

	@Test
	public void translateUnion() {
		try {
			final int parallelism = 8;
			ExecutionEnvironment env = ExecutionEnvironment.createLocalEnvironment(parallelism);

			DataSet<Tuple1<Integer>> ds1 =
					env.fromElements(new Tuple1<>(1)).setParallelism(1);
			DataSet<Tuple1<Integer>> ds2 =
					env.fromElements(new Tuple1<>(1)).setParallelism(3);

			ds1.union(ds2).output(new DiscardingOutputFormat<>());

			Plan p = env.createProgramPlan();

			GenericDataSinkBase<?> sink = p.getDataSinks().iterator().next();

			Union union = (Union) sink.getInput();

			// The parallelism should be the maximum one of its inputs.
			Assert.assertEquals(union.getParallelism(), 3);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			fail("Test caused an error: " + e.getMessage());
		}
	}
}
