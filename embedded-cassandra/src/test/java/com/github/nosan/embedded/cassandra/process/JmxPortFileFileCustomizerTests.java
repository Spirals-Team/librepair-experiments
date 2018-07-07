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

package com.github.nosan.embedded.cassandra.process;

import de.flapdoodle.embed.process.distribution.Platform;
import org.junit.Before;
import org.junit.Test;

import com.github.nosan.embedded.cassandra.Config;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link JmxPortFileCustomizer}.
 *
 * @author Dmytro Nosan
 */
public class JmxPortFileFileCustomizerTests extends AbstractFileCustomizerSupport {

	private final JmxPortFileCustomizer customizer = new JmxPortFileCustomizer();

	private TestProcessContext context;

	@Before
	public void setUp() {
		Config config = new Config();
		config.setJmxPort(9000);
		this.context = new TestProcessContext().withConfig(config);
	}

	@Test
	public void customizeInvalid() throws Exception {
		withFile("cassandra.sh").from(classpath("env.sh")).accept((file) -> {
			this.customizer.customize(file, this.context);
			assertThat(file).hasSameContentAs(classpath("env.sh"));
		});
	}

	@Test
	public void customizeSh() throws Exception {
		withFile("cassandra-env.sh").from(classpath("env.sh")).accept((file) -> {
			this.customizer.customize(file, this.context.withPlatform(Platform.Linux));
			assertThat(file).hasSameContentAs(classpath("customizers/jmx_port/env.sh"));
		});
	}

	@Test
	public void customizePs1() throws Exception {
		withFile("cassandra-env.ps1").from(classpath("env.ps1")).accept((file) -> {
			this.customizer.customize(file, this.context.withPlatform(Platform.Windows));
			assertThat(file).hasSameContentAs(classpath("customizers/jmx_port/env.ps1"));
		});
	}

}
