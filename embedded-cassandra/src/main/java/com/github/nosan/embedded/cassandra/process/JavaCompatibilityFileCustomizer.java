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
import java.io.IOException;

import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.distribution.Platform;

/**
 * Java compatability {@link FileCustomizer File Customizer}. Customizer tries to support
 * different java versions. Customizer removes all inappropriate properties.
 *
 * @author Dmytro Nosan
 */
class JavaCompatibilityFileCustomizer implements FileCustomizer {

	private static final FileCustomizer[] FILE_CUSTOMIZERS = new FileCustomizer[] {
			new JVMCompatibilityOptionsCustomizer(),
			new EnvironmentCompatibilityCustomizer() };

	@Override
	public void customize(File file, Context context) throws IOException {
		for (FileCustomizer fileCustomizer : FILE_CUSTOMIZERS) {
			fileCustomizer.customize(file, context);
		}
	}

	private static final class JVMCompatibilityOptionsCustomizer
			extends AbstractSourceLineFileCustomizer {

		@Override
		protected String process(String line, File file, Context context) {
			return (!line.contains("-XX:") ? line : "#" + line);

		}

		@Override
		protected boolean isMatch(File file, Context context) {
			String fileName = file.getName();
			return fileName.equals("jvm.options");
		}

	}

	private static final class EnvironmentCompatibilityCustomizer
			extends AbstractSourceLineFileCustomizer {

		@Override
		protected String process(String line, File file, Context context) {
			return (!line.contains("-Xloggc") ? line : "#" + line);

		}

		@Override
		protected boolean isMatch(File file, Context context) {
			String fileName = file.getName();
			Distribution distribution = context.getDistribution();
			return (distribution.getPlatform() != Platform.Windows
					? fileName.equals("cassandra-env.sh")
					: fileName.equals("cassandra-env.ps1"));
		}

	}

}
