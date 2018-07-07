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

import java.util.Arrays;

import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.config.process.ProcessConfig;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.distribution.Platform;
import de.flapdoodle.embed.process.io.IStreamProcessor;
import de.flapdoodle.embed.process.io.StreamToLineProcessor;
import de.flapdoodle.embed.process.runtime.ProcessControl;
import de.flapdoodle.embed.process.runtime.Processes;

import com.github.nosan.embedded.cassandra.ExecutableConfig;

/**
 * Utility class to stop an embedded cassandra process.
 *
 * @author Dmytro Nosan
 */
class ProcessStopper {

	void stop(ProcessContext context, ProcessControl process) {
		long pid = process.getPid();
		Platform platform = context.getDistribution().getPlatform();
		if (Processes.isProcessRunning(platform, pid)) {
			tryStop(context, pid);
			try {
				process.stop();
			}
			catch (RuntimeException ex) {
				if (Processes.isProcessRunning(platform, pid)) {
					throw ex;
				}
			}
		}
	}

	private void tryStop(ProcessContext context, long pid) {
		Distribution distribution = context.getDistribution();
		Platform platform = distribution.getPlatform();
		if (platform.isUnixLike()) {
			killProcess(context, pid);
		}
		else {
			taskKill(context, pid);
		}
	}

	private void killProcess(ProcessContext context, long pid) {
		IRuntimeConfig runtimeConfig = context.getRuntimeConfig();
		ExecutableConfig executableConfig = context.getExecutableConfig();
		IStreamProcessor output = StreamToLineProcessor
				.wrap(runtimeConfig.getProcessOutput().getCommands());
		ProcessControl.executeCommandLine(executableConfig.supportConfig(),
				"[kill process]",
				new ProcessConfig(Arrays.asList("kill", "-9", "" + pid), output));
	}

	private void taskKill(ProcessContext context, long pid) {
		IRuntimeConfig runtimeConfig = context.getRuntimeConfig();
		ExecutableConfig executableConfig = context.getExecutableConfig();
		IStreamProcessor output = StreamToLineProcessor
				.wrap(runtimeConfig.getProcessOutput().getCommands());
		ProcessControl.executeCommandLine(executableConfig.supportConfig(),
				"[taskkill" + " process]", new ProcessConfig(
						Arrays.asList("taskkill", "/F", "/T", "/pid", "" + pid), output));
	}

}
