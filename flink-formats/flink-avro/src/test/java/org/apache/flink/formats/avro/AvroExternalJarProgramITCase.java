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

package org.apache.flink.formats.avro;

import org.apache.flink.client.program.PackagedProgram;
import org.apache.flink.client.program.ProgramInvocationException;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.fs.Path;
import org.apache.flink.formats.avro.testjar.AvroExternalJarProgram;
import org.apache.flink.test.util.MiniClusterResource;
import org.apache.flink.testutils.category.Flip6;
import org.apache.flink.util.TestLogger;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.util.Collections;

/**
 * IT case for the {@link AvroExternalJarProgram}.
 */
@Category(Flip6.class)
public class AvroExternalJarProgramITCase extends TestLogger {

	private static final String JAR_FILE = "maven-test-jar.jar";

	private static final String TEST_DATA_FILE = "/testdata.avro";

	@Rule
	public final MiniClusterResource MINI_CLUSTER_RESOURCE = new MiniClusterResource(
		new MiniClusterResource.MiniClusterResourceConfiguration(
			new Configuration(),
			1,
			4));

	@Test
	public void testExternalProgram() throws ProgramInvocationException {
		String testData = getClass().getResource(TEST_DATA_FILE).toString();

		PackagedProgram program = new PackagedProgram(new File(JAR_FILE), new String[] { testData });

		MINI_CLUSTER_RESOURCE.setupTestEnvironment(Collections.singleton(new Path(JAR_FILE)), Collections.emptyList());

		program.invokeInteractiveModeForExecution();
	}
}
