/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nosan.embedded.cassandra.customizer;

import java.io.File;

import de.flapdoodle.embed.process.distribution.Distribution;

/**
 * todo leave javadoc here.
 *
 * @author Dmytro Nosan
 */
public class JVMOptionsFileCustomizer extends AbstractFileCustomizer {

	private static final String[] EXCLUDE = new String[] { "-XX:+PrintGCDetails",
			"-XX:+PrintGCDateStamps", "-XX:+PrintHeapAtGC",
			"-XX:+PrintTenuringDistribution", "-XX:+PrintGCApplicationStoppedTime",
			"-XX:+PrintPromotionFailure", "-XX:PrintFLSStatistics", "-Xloggc:",
			"-XX:+UseGCLogFileRotation", "-XX:NumberOfGCLogFiles", "-XX:GCLogFileSize",
			"-XX:+UseThreadPriorities", "-XX:ThreadPriorityPolicy" };

	@Override
	protected boolean isMatch(File file, Distribution distribution) {
		return file.getName().equals("jvm.options");
	}

	@Override
	protected String process(String line) {
		for (String e : EXCLUDE) {
			if (line.trim().startsWith(e)) {
				return "#" + line;
			}
		}
		return line;
	}

}
