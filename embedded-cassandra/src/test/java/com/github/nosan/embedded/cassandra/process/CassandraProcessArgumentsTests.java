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
import de.flapdoodle.embed.process.extract.IExtractedFileSet;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ProcessStarter.Arguments}.
 *
 * @author Dmytro Nosan
 */
public class CassandraProcessArgumentsTests {

	@Test
	public void unixLike() {
		ProcessStarter.Arguments arguments = new ProcessStarter.Arguments();
		TestProcessContext context = new TestProcessContext()
				.withPlatform(Platform.Linux);
		IExtractedFileSet fileSet = context.getExtractedFileSet();
		assertThat(arguments.get(context))
				.containsExactly(fileSet.executable().getAbsolutePath(), "-f");

	}

	@Test
	public void windows() {
		ProcessStarter.Arguments arguments = new ProcessStarter.Arguments();
		TestProcessContext context = new TestProcessContext()
				.withPlatform(Platform.Windows);
		IExtractedFileSet fileSet = context.getExtractedFileSet();
		assertThat(arguments.get(context)).containsExactly("powershell",
				"-ExecutionPolicy", "Bypass", fileSet.executable().getAbsolutePath(),
				"-f");

	}

}
