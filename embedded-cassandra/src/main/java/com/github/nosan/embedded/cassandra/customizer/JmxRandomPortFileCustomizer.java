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

import com.github.nosan.embedded.cassandra.util.PortUtils;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.distribution.Platform;

/**
 * JMX {@link FileCustomizer} to set random port.
 *
 * @author Dmytro Nosan
 */
public class JmxRandomPortFileCustomizer extends AbstractSourceLineFileCustomizer {

	private static final String[] CANDIDATES = new String[] { "$JMX_PORT=", "JMX_PORT=" };

	@Override
	protected boolean isMatch(File file, Distribution distribution) {
		String fileName = file.getName();
		return (distribution.getPlatform() != Platform.Windows
				? fileName.equals("cassandra-env.sh")
				: fileName.equals("cassandra-env.ps1"));
	}

	@Override
	protected String process(String line, File file, Distribution distribution) {
		String trimmed = line.trim();
		for (String candidate : CANDIDATES) {
			if (trimmed.startsWith(candidate)) {
				return candidate + "=" + PortUtils.getRandomPort();
			}
		}
		return line;
	}

}
