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
 * Environment {@link FileCustomizer} to comment JVM_OPTS="$JVM_OPTS
 * -Xloggc:${CASSANDRA_HOME}/logs/gc.log".
 *
 * @author Dmytro Nosan
 */
public class Java9FileCustomizer extends Java8FileCustomizer {

	private static final String[] COMMENT_CANDIDATE = new String[] {
			"-XX:+UseThreadPriorities", "-XX:ThreadPriorityPolicy",
			"-XX:+PrintGCDateStamps", "-XX:+UseParNewGC", "-XX:+UseConcMarkSweepGC" };

	@Override
	protected String process(String line, File file, Distribution distribution) {
		for (String candidate : COMMENT_CANDIDATE) {
			if (line.trim().startsWith(candidate)) {
				return "#" + line;
			}
		}
		return super.process(line, file, distribution);
	}

}
