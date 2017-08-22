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

package org.apache.flink.graph.asm.degree.annotate.undirected;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.graph.Edge;
import org.apache.flink.graph.asm.AsmTestBase;
import org.apache.flink.graph.asm.dataset.ChecksumHashCode;
import org.apache.flink.graph.asm.dataset.ChecksumHashCode.Checksum;
import org.apache.flink.test.util.TestBaseUtils;
import org.apache.flink.types.IntValue;
import org.apache.flink.types.LongValue;
import org.apache.flink.types.NullValue;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EdgeSourceDegreeTest
extends AsmTestBase {

	@Test
	public void testWithSimpleGraph()
			throws Exception {
		String expectedResult =
			"(0,1,((null),2))\n" +
			"(0,2,((null),2))\n" +
			"(1,0,((null),3))\n" +
			"(1,2,((null),3))\n" +
			"(1,3,((null),3))\n" +
			"(2,0,((null),3))\n" +
			"(2,1,((null),3))\n" +
			"(2,3,((null),3))\n" +
			"(3,1,((null),4))\n" +
			"(3,2,((null),4))\n" +
			"(3,4,((null),4))\n" +
			"(3,5,((null),4))\n" +
			"(4,3,((null),1))\n" +
			"(5,3,((null),1))";

		DataSet<Edge<IntValue, Tuple2<NullValue, LongValue>>> sourceDegreeOnSourceId = undirectedSimpleGraph
			.run(new EdgeSourceDegree<IntValue, NullValue, NullValue>());

		TestBaseUtils.compareResultAsText(sourceDegreeOnSourceId.collect(), expectedResult);

		DataSet<Edge<IntValue, Tuple2<NullValue, LongValue>>> sourceDegreeOnTargetId = undirectedSimpleGraph
			.run(new EdgeSourceDegree<IntValue, NullValue, NullValue>()
				.setReduceOnTargetId(true));

		TestBaseUtils.compareResultAsText(sourceDegreeOnTargetId.collect(), expectedResult);
	}

	@Test
	public void testWithRMatGraph()
			throws Exception {
		DataSet<Edge<LongValue, Tuple2<NullValue, LongValue>>> sourceDegreeOnSourceId = undirectedRMatGraph
			.run(new EdgeSourceDegree<LongValue, NullValue, NullValue>());

		Checksum checksumOnSourceId = new ChecksumHashCode<Edge<LongValue, Tuple2<NullValue, LongValue>>>()
			.run(sourceDegreeOnSourceId)
			.execute();

		assertEquals(20884, checksumOnSourceId.getCount());
		assertEquals(0x000000019d8f0070L, checksumOnSourceId.getChecksum());

		DataSet<Edge<LongValue, Tuple2<NullValue, LongValue>>> sourceDegreeOnTargetId = undirectedRMatGraph
			.run(new EdgeSourceDegree<LongValue, NullValue, NullValue>()
				.setReduceOnTargetId(true));

		Checksum checksumOnTargetId = new ChecksumHashCode<Edge<LongValue, Tuple2<NullValue, LongValue>>>()
			.run(sourceDegreeOnTargetId)
			.execute();

		assertEquals(checksumOnTargetId, checksumOnTargetId);
	}
}
