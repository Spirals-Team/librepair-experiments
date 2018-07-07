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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.distribution.Platform;
import de.flapdoodle.embed.process.extract.IExtractedFileSet;
import de.flapdoodle.embed.process.runtime.ProcessControl;

import com.github.nosan.embedded.cassandra.ExecutableConfig;

/**
 * Utility class to start an embedded cassandra process.
 *
 * @author Dmytro Nosan
 */
class ProcessStarter {

	ProcessControl start(ProcessContext processContext) throws IOException {
		ExecutableConfig executableConfig = processContext.getExecutableConfig();
		Arguments arguments = new Arguments();
		List<String> args = arguments.get(processContext);
		ProcessBuilder processBuilder = ProcessControl.newProcessBuilder(args, false);
		return ProcessControl.start(executableConfig.supportConfig(), processBuilder);
	}

	/**
	 * Utility class for creating command line.
	 */
	static final class Arguments {

		List<String> get(ProcessContext processContext) {

			IExtractedFileSet fileSet = processContext.getExtractedFileSet();
			Distribution distribution = processContext.getDistribution();
			IRuntimeConfig runtimeConfig = processContext.getRuntimeConfig();

			List<String> args = new ArrayList<>();
			if (distribution.getPlatform() == Platform.Windows) {
				args.add("powershell");
				args.add("-ExecutionPolicy");
				args.add("Bypass");
			}
			args.add(fileSet.executable().getAbsolutePath());
			args.add("-f");

			return runtimeConfig.getCommandLinePostProcessor().process(distribution,
					args);
		}

	}

}
