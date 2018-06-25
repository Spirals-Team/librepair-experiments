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
 * Java 9 {@link FileCustomizer}.
 *
 * @author Dmytro Nosan
 */
public class Java9Customizer extends AbstractSourceLineFileCustomizer {

	private static final String[] CANDIDATES = new String[] { "-XX:+UseThreadPriorities",
			"-XX:ThreadPriorityPolicy", "-XX:+PrintGCDateStamps", "-XX:+UseParNewGC",
			"-XX:+UseConcMarkSweepGC", "-XX:+PrintHeapAtGC",
			"-XX:+PrintTenuringDistribution", "-XX:+PrintGCApplicationStoppedTime" };

	@Override
	protected String process(String line, File file, Distribution distribution) {
		for (String candidate : CANDIDATES) {
			if (line.trim().startsWith(candidate)) {
				return "#" + line;
			}
		}
		return line;
	}

	@Override
	protected boolean isMatch(File file, Distribution distribution) {
		return file.getName().equals("jvm.options");
	}

}
