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
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;

import com.github.nosan.embedded.cassandra.config.Version;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.distribution.Platform;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link JmxPortCustomizer}.
 *
 * @author Dmytro Nosan
 */
public class JmxPortCustomizerTests {

	private final JmxPortCustomizer customizer = new JmxPortCustomizer(9000);

	@Test
	public void customize() throws Exception {
		Distribution distribution = Distribution.detectFor(Version.LATEST);
		File file = new File(getName(distribution));
		try (PrintWriter ps = new PrintWriter(new FileWriter(file))) {
			ps.println("JMX_PORT=\"7199\"\n$JMX_PORT=\"7199\"");
		}
		try {
			this.customizer.customize(file, distribution);
			assertThat(file).hasContent("JMX_PORT=\"9000\"\n$JMX_PORT=\"9000\"");
		}
		finally {
			Files.deleteIfExists(file.toPath());
		}
	}

	protected final String getName(Distribution distribution) {
		return (distribution.getPlatform() != Platform.Windows ? "cassandra-env.sh"
				: "cassandra-env.ps1");
	}

}
