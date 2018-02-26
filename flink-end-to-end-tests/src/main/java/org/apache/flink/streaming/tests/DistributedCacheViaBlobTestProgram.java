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

package org.apache.flink.streaming.tests;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.util.Collections.singletonList;

/**
 * End-to-end test program for verifying that files are distributed via BlobServer and later accessible through
 * DistribitutedCache. We verify that via uploading file and later on accessing it in map function. To be sure we read
 * version read from cache, we delete the initial file.
 */
public class DistributedCacheViaBlobTestProgram {

	public static void main(String[] args) throws Exception {

		final ParameterTool params = ParameterTool.fromArgs(args);

		final String fileContent = params.getRequired("content");
		final String tempDir = params.getRequired("tempDir");

		final File tempFile = File.createTempFile("temp", null, new File(tempDir));
		Files.write(tempFile.toPath(), singletonList(fileContent));

		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		env.registerUserArtifact(tempFile.getPath(), "test_data");

		env.fromElements(1)
			.map(new TestMapFunction(tempFile.getAbsolutePath()))
			.writeAsText(params.getRequired("output"), FileSystem.WriteMode.OVERWRITE);

		env.execute("Distributed Cache Via Blob Test Program");
	}

	static class TestMapFunction extends RichMapFunction<Integer, String> {

		private String initialPath;

		public TestMapFunction(String initialPath) {
			this.initialPath = initialPath;
		}

		@Override
		public String map(Integer value) throws Exception {
			Files.deleteIfExists(Paths.get(initialPath));
			return StringUtils.join(Files
				.readAllLines(getRuntimeContext().getDistributedCache().getFile("test_data").toPath()), "\n");
		}
	}
}
