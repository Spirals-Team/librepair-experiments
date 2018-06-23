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

package com.github.nosan.embedded.cassandra;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.github.nosan.embedded.cassandra.config.CassandraConfig;
import com.github.nosan.embedded.cassandra.config.CassandraProcessConfig;
import com.github.nosan.embedded.cassandra.util.PortUtils;
import com.github.nosan.embedded.cassandra.util.YamlUtils;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.config.ISupportConfig;
import de.flapdoodle.embed.process.config.io.ProcessOutput;
import de.flapdoodle.embed.process.config.process.ProcessConfig;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.distribution.Platform;
import de.flapdoodle.embed.process.extract.IExtractedFileSet;
import de.flapdoodle.embed.process.io.IStreamProcessor;
import de.flapdoodle.embed.process.io.LogWatchStreamProcessor;
import de.flapdoodle.embed.process.io.Processors;
import de.flapdoodle.embed.process.io.StreamToLineProcessor;
import de.flapdoodle.embed.process.runtime.AbstractProcess;
import de.flapdoodle.embed.process.runtime.ProcessControl;
import de.flapdoodle.embed.process.runtime.Processes;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link AbstractProcess} for an embedded cassandra server.
 *
 * @author Dmytro Nosan
 */
public class CassandraServerProcess extends
		AbstractProcess<CassandraProcessConfig, CassandraServerExecutable, CassandraServerProcess> {

	private static final Logger log = LoggerFactory
			.getLogger(CassandraServerProcess.class);

	private ProcessStopper stopper;

	private IRuntimeConfig runtimeConfig;

	CassandraServerProcess(Distribution distribution, CassandraProcessConfig config,
			IRuntimeConfig runtimeConfig, CassandraServerExecutable executable)
			throws IOException {
		super(distribution, config, runtimeConfig, executable);
	}

	@Override
	protected void onBeforeProcess(IRuntimeConfig runtimeConfig) throws IOException {
		this.runtimeConfig = runtimeConfig;
		super.onBeforeProcess(runtimeConfig);
	}

	@Override
	protected List<String> getCommandLine(Distribution distribution,
			CassandraProcessConfig cassandraProcessConfig, IExtractedFileSet fileSet)
			throws IOException {

		this.stopper = new ProcessStopper(distribution, this.runtimeConfig,
				cassandraProcessConfig.supportConfig());

		ProcessCommand processCommand = new ProcessCommand(distribution,
				cassandraProcessConfig, fileSet);

		List<String> args = processCommand.getArgs();

		log.info("Starting the new cassandra server using directory '" + fileSet.baseDir()
				+ "' with arguments " + args);

		return args;
	}

	@Override
	protected void onAfterProcessStart(ProcessControl process,
			IRuntimeConfig runtimeConfig) throws IOException {
		setProcessId(getProcessId());
		ProcessWaiter processWaiter = new ProcessWaiter(runtimeConfig, getConfig(),
				process);
		processWaiter.await();
		log.info("Embedded Cassandra has been started.");

	}

	@Override
	protected void stopInternal() {
		ProcessStopper stopper = this.stopper;
		if (isProcessRunning() && stopper != null) {
			log.info("Stopping the cassandra server...");
			if (!stopper.stop(getProcessId())) {
				log.warn("Could not stop cassandra server. Trying to destroy it.");
			}
			stopProcess();
		}
	}

	@Override
	protected void cleanupInternal() {

	}

	/**
	 * Utility class for waiting cassandra process.
	 */
	private static final class ProcessWaiter {

		private final IRuntimeConfig runtimeConfig;

		private final CassandraProcessConfig config;

		private final ProcessControl process;

		ProcessWaiter(IRuntimeConfig runtimeConfig, CassandraProcessConfig config,
				ProcessControl process) {
			this.runtimeConfig = runtimeConfig;
			this.config = config;
			this.process = process;
		}

		void await() throws IOException {

			ProcessOutput outputConfig = this.runtimeConfig.getProcessOutput();
			CassandraProcessConfig cassandraProcessConfig = this.config;
			Duration timeout = cassandraProcessConfig.getTimeout();
			CassandraConfig config = cassandraProcessConfig.getConfig();

			LogWatchStreamProcessor logWatch = new LogWatchStreamProcessor(
					getSuccess(config), getFailures(),
					StreamToLineProcessor.wrap(outputConfig.getOutput()));

			Processors.connect(this.process.getReader(), logWatch);
			Processors.connect(this.process.getError(),
					StreamToLineProcessor.wrap(outputConfig.getError()));

			long mark = System.currentTimeMillis();
			logWatch.waitForResult(timeout.toMillis());

			if (!logWatch.isInitWithSuccess()) {
				String failureFound = logWatch.getFailureFound();
				throw new IOException("Could not start process. "
						+ ObjectUtils.defaultIfNull(failureFound, ""));
			}

			if (!tryConnect(timeout.minusMillis(System.currentTimeMillis() - mark),
					config)) {
				throw new IOException(
						"Could not start process. Please increase your startup timeout");
			}

		}

		private LinkedHashSet<String> getFailures() {
			return new LinkedHashSet<>(Arrays.asList("encountered during startup",
					"Missing required", "Address already in use", "Port already in use"));
		}

		private String getSuccess(CassandraConfig config) {

			if (config.isStartNativeTransport()) {
				return "Starting listening for CQL";
			}

			if (config.isStartRpc()) {
				return "Listening for thrift clients";
			}

			if (config.getListenInterface() != null || config.getRpcInterface() != null) {
				return "Starting Messaging Service";
			}

			return "Not starting";
		}

		private boolean tryConnect(Duration timeout, CassandraConfig config) {
			long startTime = System.nanoTime();
			long rem = timeout.toNanos();
			do {
				if (tryConnect(config)) {
					return true;
				}
				if (rem > 0) {
					try {
						Thread.sleep(
								Math.min(TimeUnit.NANOSECONDS.toMillis(rem) + 1, 500));
					}
					catch (InterruptedException ex) {
						Thread.currentThread().interrupt();
					}
				}
				rem = timeout.toNanos() - (System.nanoTime() - startTime);
			}
			while (rem > 0);
			return false;

		}

		private boolean tryConnect(CassandraConfig config) {
			if (config.isStartNativeTransport()) {
				return tryConnect(
						ObjectUtils.defaultIfNull(config.getListenAddress(), "localhost"),
						config.getNativeTransportPort());
			}
			else if (config.isStartRpc()) {
				return tryConnect(
						ObjectUtils.defaultIfNull(config.getRpcAddress(), "localhost"),
						config.getRpcPort());
			}
			return true;
		}

		private boolean tryConnect(String host, int port) {
			try (Socket ignored = new Socket(host, port)) {
				return true;
			}
			catch (IOException ex) {
				return false;
			}
		}

	}

	/**
	 * Utility class for destroying cassandra process.
	 */
	private static final class ProcessStopper {

		private final Distribution distribution;

		private final IRuntimeConfig runtimeConfig;

		private final ISupportConfig supportConfig;

		ProcessStopper(Distribution distribution, IRuntimeConfig runtimeConfig,
				ISupportConfig supportConfig) {
			this.distribution = distribution;
			this.runtimeConfig = runtimeConfig;
			this.supportConfig = supportConfig;
		}

		boolean stop(long pid) {
			Platform platform = this.distribution.getPlatform();
			if (platform.isUnixLike()) {
				return killProcess(pid);
			}
			return taskKill(pid);

		}

		private boolean killProcess(long pid) {
			IStreamProcessor output = StreamToLineProcessor
					.wrap(this.runtimeConfig.getProcessOutput().getCommands());
			ISupportConfig sc = this.supportConfig;
			Platform pl = this.distribution.getPlatform();
			return Processes.killProcess(sc, pl, output, pid)
					|| Processes.termProcess(sc, pl, output, pid)
					|| ProcessControl.executeCommandLine(sc, "[kill process]",
							new ProcessConfig(Arrays.asList("kill", "-9", "" + pid),
									output));
		}

		private boolean taskKill(long pid) {
			IStreamProcessor output = StreamToLineProcessor
					.wrap(this.runtimeConfig.getProcessOutput().getCommands());
			return ProcessControl.executeCommandLine(this.supportConfig,
					"[taskkill process]",
					new ProcessConfig(
							Arrays.asList("taskkill", "/F", "/T", "/pid", "" + pid),
							output));
		}

	}

	/**
	 * Utility class for building command line.
	 */
	private static final class ProcessCommand {

		private final Distribution distribution;

		private final CassandraProcessConfig processConfig;

		private final IExtractedFileSet fileSet;

		ProcessCommand(Distribution distribution, CassandraProcessConfig processConfig,
				IExtractedFileSet fileSet) {
			this.distribution = distribution;
			this.processConfig = processConfig;
			this.fileSet = fileSet;
		}

		List<String> getArgs() throws IOException {

			List<String> args = new ArrayList<>();

			File configurationFile = new File(this.fileSet.baseDir(),
					"cassandra-" + UUID.randomUUID() + ".yaml");
			try (FileWriter writer = new FileWriter(configurationFile)) {
				YamlUtils.serialize(this.processConfig.getConfig(), writer);
			}

			if (this.distribution.getPlatform() == Platform.Windows) {
				args.add("powershell");
				args.add("-ExecutionPolicy");
				args.add("Bypass");
			}

			args.add(this.fileSet.executable().getAbsolutePath());
			args.add("-f");

			args.add(getConfig(configurationFile));
			args.add(getJmxPort());
			return args;
		}

		private String getJmxPort() {
			StringBuilder arg = new StringBuilder();
			if (this.distribution.getPlatform() == Platform.Windows) {
				arg.append("`");
			}
			arg.append("-Dcassandra.jmx.local.port=");
			arg.append(PortUtils.getRandomPort());
			return arg.toString();
		}

		private String getConfig(File configurationFile) {
			StringBuilder arg = new StringBuilder();
			if (this.distribution.getPlatform() == Platform.Windows) {
				arg.append("`");
			}
			arg.append("-Dcassandra.config=file:");
			arg.append(StringUtils.repeat(File.separatorChar, 3));
			arg.append(configurationFile.getAbsolutePath());

			return arg.toString();
		}

	}

}
