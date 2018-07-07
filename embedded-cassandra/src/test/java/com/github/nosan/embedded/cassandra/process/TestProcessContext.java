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

import java.io.File;

import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.distribution.Platform;
import de.flapdoodle.embed.process.extract.IExtractedFileSet;
import de.flapdoodle.embed.process.extract.ImmutableExtractedFileSet;

import com.github.nosan.embedded.cassandra.Config;
import com.github.nosan.embedded.cassandra.ExecutableConfig;
import com.github.nosan.embedded.cassandra.ExecutableVersion;
import com.github.nosan.embedded.cassandra.Version;
import com.github.nosan.embedded.cassandra.support.ExecutableConfigBuilder;
import com.github.nosan.embedded.cassandra.support.RuntimeConfigBuilder;

/**
 * todo leave javadoc here.
 *
 * @author Dmytro Nosan
 */
class TestProcessContext extends ProcessContext {

	TestProcessContext() {
		super(Distribution.detectFor(new ExecutableVersion(Version.LATEST)),
				new RuntimeConfigBuilder().build(), new ExecutableConfigBuilder().build(),
				ImmutableExtractedFileSet.builder(new File(".embedded-cassandra"))
						.executable(new File("cassandra")).build());
	}

	private TestProcessContext(Distribution distribution, IRuntimeConfig runtimeConfig,
			ExecutableConfig executableConfig, IExtractedFileSet extractedFileSet) {
		super(distribution, runtimeConfig, executableConfig, extractedFileSet);
	}

	TestProcessContext withConfig(Config config) {
		ExecutableConfig executableConfig = getExecutableConfig();
		return new TestProcessContext(getDistribution(), getRuntimeConfig(),
				new ExecutableConfigBuilder().config(config)
						.timeout(executableConfig.getTimeout())
						.version(executableConfig.getVersion()).build(),
				getExtractedFileSet());
	}

	TestProcessContext withPlatform(Platform platform) {
		Distribution distribution = getDistribution();
		return new TestProcessContext(
				new Distribution(distribution.getVersion(), platform,
						distribution.getBitsize()),
				getRuntimeConfig(), getExecutableConfig(), getExtractedFileSet());
	}

	TestProcessContext withDistribution(Distribution distribution) {
		return new TestProcessContext(distribution, getRuntimeConfig(),
				getExecutableConfig(), getExtractedFileSet());
	}

	TestProcessContext withFileSet(IExtractedFileSet fileSet) {
		return new TestProcessContext(getDistribution(), getRuntimeConfig(),
				getExecutableConfig(), fileSet);
	}

}
