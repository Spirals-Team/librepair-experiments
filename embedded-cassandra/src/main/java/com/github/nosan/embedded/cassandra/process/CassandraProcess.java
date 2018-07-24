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
import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;

import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.config.ISupportConfig;
import de.flapdoodle.embed.process.config.io.ProcessOutput;
import de.flapdoodle.embed.process.config.process.ProcessConfig;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.distribution.Platform;
import de.flapdoodle.embed.process.io.IStreamProcessor;
import de.flapdoodle.embed.process.io.Processors;
import de.flapdoodle.embed.process.io.StreamToLineProcessor;
import de.flapdoodle.embed.process.runtime.IStopable;
import de.flapdoodle.embed.process.runtime.ProcessControl;
import de.flapdoodle.embed.process.runtime.Processes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.nosan.embedded.cassandra.ExecutableConfig;

/**
 * Basic implementation of Cassandra Process.
 *
 * @author Dmytro Nosan
 */
public final class CassandraProcess implements IStopable {


	private static final Logger log = LoggerFactory.getLogger(CassandraProcess.class);

	private ProcessControl process;

	private final Context context;

	CassandraProcess(Context context) {
		this.context = Objects.requireNonNull(context, "Context must not be null");
	}


	/**
	 * Start a new cassandra process using following steps:
	 * <ol>
	 * <li>Invokes Customizers</li>
	 * <li>Create a command line</li>
	 * <li>Create a process</li>
	 * <li>Watching logs</li>
	 * <li>Check Transport connection</li>
	 * </ol>.
	 *
	 * @throws IOException IOCassandra's process has not been started correctly.
	 */
	void start() throws IOException {
		ExecutableConfig executableConfig = this.context.getExecutableConfig();
		IRuntimeConfig runtimeConfig = this.context.getRuntimeConfig();
		ISupportConfig supportConfig = executableConfig.supportConfig();
		ProcessOutput processOutput = runtimeConfig.getProcessOutput();
		CustomizerUtils.customize(this.context);
		ProcessBuilder processBuilder = ProcessControl.newProcessBuilder(ArgumentUtils.get(this.context), true);
		log.info("Starting Cassandra Process with a command line {}", processBuilder.command());
		this.process = ProcessControl.start(supportConfig, processBuilder);

		LogWatchProcessor logWatch = new LogWatchProcessor(executableConfig.getConfig(), processOutput.getOutput());
		Processors.connect(this.process.getReader(), StreamToLineProcessor.wrap(logWatch));
		Processors.connect(this.process.getError(), StreamToLineProcessor.wrap(processOutput.getError()));

		long millis = executableConfig.getTimeout().toMillis();
		logWatch.waitForResult(millis);

		if (!logWatch.isInitWithSuccess()) {
			String msg = "Could not start a process '" + getPid(this.process) + "'. Timeout : " + millis + " ms." +
					"\nFailure:" + logWatch.getFailureFound() + "\nOutput:\n----- START ----- \n" +
					logWatch.getOutput() + "----- END ----- \n" + "Support Url:\t" +
					executableConfig.supportConfig().getSupportUrl() + "\n";
			throw new IOException(msg);
		}

		TransportUtils.check(executableConfig.getConfig(), 10, Duration.ofSeconds(2));
		log.info("Cassandra process '{}' has been started.", getPid(this.process));

	}

	/**
	 * Stop Cassandra's process depends on platform.
	 * Invokes {@code kill -9} for unix like and {@code taskkill /F /T} for windows.
	 */
	@Override
	public void stop() {
		long pid = getPid(this.process);
		if (isRunning(this.process, this.context)) {
			tryStop(this.context, pid);
			try {
				this.process.stop();
			}
			catch (Exception ex) {
				if (isRunning(this.process, this.context)) {
					log.error(String.format("Cassandra process '%s' still running", pid), ex);
					return;
				}
			}

			try {
				this.process.waitFor();
			}
			catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			log.info("Cassandra process '{}' has been stopped.", pid);
		}
	}

	@Override
	public boolean isRegisteredJobKiller() {
		return false;
	}


	private static boolean isRunning(ProcessControl process, Context context) {
		if (getPid(process) > 0) {
			Distribution distribution = context.getDistribution();
			return Processes.isProcessRunning(distribution.getPlatform(), getPid(process));
		}
		return false;
	}

	private static long getPid(ProcessControl process) {
		Long pid = null;
		if (process != null) {
			pid = process.getPid();
		}
		return (pid != null ? pid : 0);
	}

	private static void tryStop(Context context, long pid) {
		Distribution distribution = context.getDistribution();
		Platform platform = distribution.getPlatform();
		if (platform.isUnixLike()) {
			killProcess(context, pid);
		}
		else {
			taskKill(context, pid);
		}
	}

	private static void killProcess(Context context, long pid) {
		IRuntimeConfig runtimeConfig = context.getRuntimeConfig();
		ExecutableConfig executableConfig = context.getExecutableConfig();
		IStreamProcessor output = StreamToLineProcessor.wrap(runtimeConfig.getProcessOutput().getCommands());
		ProcessControl.executeCommandLine(executableConfig.supportConfig(), "[kill process]",
				new ProcessConfig(Arrays.asList("kill", "-9", "" + pid), output));
	}

	private static void taskKill(Context context, long pid) {
		IRuntimeConfig runtimeConfig = context.getRuntimeConfig();
		ExecutableConfig executableConfig = context.getExecutableConfig();
		IStreamProcessor output = StreamToLineProcessor.wrap(runtimeConfig.getProcessOutput().getCommands());
		ProcessControl.executeCommandLine(executableConfig.supportConfig(), "[taskkill process]", new ProcessConfig(
				Arrays.asList("taskkill", "/F", "/T", "/pid", "" + pid),
				output));
	}

}
