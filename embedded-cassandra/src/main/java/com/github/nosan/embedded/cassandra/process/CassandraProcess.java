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
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import de.flapdoodle.embed.process.config.IRuntimeConfig;
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
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.nosan.embedded.cassandra.Config;
import com.github.nosan.embedded.cassandra.ExecutableConfig;

/**
 * Basic implementation of {@link AbstractProcess} for Cassandra.
 *
 * @author Dmytro Nosan
 */
public final class CassandraProcess
		extends AbstractProcess<ExecutableConfig, CassandraExecutable, CassandraProcess> {

	private static final Logger log = LoggerFactory.getLogger(CassandraProcess.class);

	private IRuntimeConfig runtimeConfig;

	private Context context;

	CassandraProcess(Distribution distribution, ExecutableConfig config,
			IRuntimeConfig runtimeConfig, CassandraExecutable executable)
			throws IOException {
		super(distribution, config, runtimeConfig, executable);
	}

	@Override
	protected List<String> getCommandLine(Distribution distribution,
			ExecutableConfig config, IExtractedFileSet fileSet) throws IOException {
		this.context = new Context(distribution, this.runtimeConfig, config, fileSet);
		List<String> args = Arguments.get(this.context);
		log.info("Starting the new cassandra server using directory '" + fileSet.baseDir()
				+ "' with arguments " + args);
		return args;
	}

	@Override
	protected void onBeforeProcess(IRuntimeConfig runtimeConfig) throws IOException {
		this.runtimeConfig = runtimeConfig;
	}

	@Override
	protected void onBeforeProcessStart(ProcessBuilder processBuilder,
			ExecutableConfig config, IRuntimeConfig runtimeConfig) {
		for (ContextCustomizer customizer : getCustomizers()) {
			customizer.customize(this.context);
		}
	}

	@Override
	protected void onAfterProcessStart(ProcessControl process,
			IRuntimeConfig runtimeConfig) throws IOException {
		setProcessId(getProcessId());
		ProcessOutput processOutput = runtimeConfig.getProcessOutput();
		ExecutableConfig executableConfig = getConfig();
		Config config = executableConfig.getConfig();
		LogWatch logWatch = new LogWatch(config, processOutput.getOutput());

		Processors.connect(process.getReader(), StreamToLineProcessor.wrap(logWatch));
		Processors.connect(process.getError(),
				StreamToLineProcessor.wrap(processOutput.getError()));

		logWatch.waitForResult(executableConfig.getTimeout().toMillis());

		if (!logWatch.isInitWithSuccess()) {
			String failureFound = logWatch.getFailureFound();
			if (failureFound == null) {
				throw new IOException("Could not start a process.");
			}
			throw new IOException("Could not start a process." + failureFound);
		}
		if (ClientConnection.isEnabled(config) && !ClientConnection.isConnected(config)) {
			throw new IOException(
					"Could not start a process. Something wrong with a client transport.");
		}
		log.info("Cassandra server has been started.");

	}

	@Override
	protected void stopInternal() {
		ProcessStopper.stop(this);

	}

	@Override
	protected void cleanupInternal() {

	}

	private List<ContextCustomizer> getCustomizers() {
		List<ContextCustomizer> customizers = new ArrayList<>();
		FileCustomizers fileCustomizers = new FileCustomizers();
		fileCustomizers.addCustomizer(new JavaCompatibilityFileCustomizer());
		fileCustomizers.addCustomizer(new JmxPortFileCustomizer());
		fileCustomizers.addCustomizer(new JVMOptionsFileCustomizer());
		fileCustomizers.addCustomizer(new ConfigFileCustomizer());
		customizers.add(new RandomPortCustomizer());
		customizers.add(fileCustomizers);
		return customizers;
	}

	/**
	 * Utility class for watching cassandra log's.
	 */
	private static final class LogWatch extends LogWatchStreamProcessor {

		LogWatch(Config config, IStreamProcessor output) {
			super(getSuccess(config), getFailures(), output);
		}

		private static String getSuccess(Config config) {

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

		private static Set<String> getFailures() {
			return new LinkedHashSet<>(Arrays.asList("encountered during startup",
					"Missing required", "Address already in use", "Port already in use",
					"ConfigurationException", "syntax error near unexpected",
					"Error occurred during initialization",
					"Cassandra 3.0 and later require Java"));
		}

	}

	/**
	 * Utility class for creating command line.
	 */
	static abstract class Arguments {

		static List<String> get(Context context) {
			IExtractedFileSet fileSet = context.getExtractedFileSet();
			Distribution distribution = context.getDistribution();
			List<String> args = new ArrayList<>();
			if (distribution.getPlatform() == Platform.Windows) {
				args.add("powershell");
				args.add("-ExecutionPolicy");
				args.add("Unrestricted");
			}
			args.add(fileSet.executable().getAbsolutePath());
			args.add("-f");
			return args;
		}

	}

	/**
	 * Utility class to stop an embedded cassandra process.
	 */
	static abstract class ProcessStopper {

		static void stop(CassandraProcess process) {
			Context context = process.context;
			long pid = process.getProcessId();
			if (process.isProcessRunning()) {
				tryStop(context, pid);
				try {
					process.stopProcess();
				}
				catch (RuntimeException ex) {
					if (process.isProcessRunning()) {
						throw ex;
					}
				}
			}
			log.info("Cassandra server has been stopped.");
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
			IStreamProcessor output = StreamToLineProcessor
					.wrap(runtimeConfig.getProcessOutput().getCommands());
			ProcessControl.executeCommandLine(executableConfig.supportConfig(),
					"[kill process]",
					new ProcessConfig(Arrays.asList("kill", "-9", "" + pid), output));
		}

		private static void taskKill(Context context, long pid) {
			IRuntimeConfig runtimeConfig = context.getRuntimeConfig();
			ExecutableConfig executableConfig = context.getExecutableConfig();
			IStreamProcessor output = StreamToLineProcessor
					.wrap(runtimeConfig.getProcessOutput().getCommands());
			ProcessControl.executeCommandLine(executableConfig.supportConfig(),
					"[taskkill" + " process]",
					new ProcessConfig(
							Arrays.asList("taskkill", "/F", "/T", "/pid", "" + pid),
							output));
		}

	}

	/**
	 * Utility class to check cassandra is ready to accept connections.
	 */
	static class ClientConnection {

		private static final Logger log = LoggerFactory.getLogger(ClientConnection.class);

		private static final String LOCALHOST = "localhost";

		private static final int MAX_ATTEMPTS = 10;

		static boolean isConnected(Config config) {
			for (int i = 0; i < MAX_ATTEMPTS; i++) {
				log.info("Trying to connect to cassandra... Attempt:" + (i + 1));
				boolean connected = tryConnect(config);
				if (connected) {
					log.info(
							"Connection to cassandra has been established successfully.");
					return true;
				}
				try {
					TimeUnit.SECONDS.sleep(2);
				}
				catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}
			log.warn("Connection to cassandra has not been established...");
			return false;
		}

		static boolean isEnabled(Config config) {
			return config.isStartNativeTransport() || config.isStartRpc();
		}

		private static boolean tryConnect(Config config) {
			if (config.isStartNativeTransport()) {
				return tryConnect(
						ObjectUtils.defaultIfNull(config.getRpcAddress(), LOCALHOST),
						config.getNativeTransportPort());
			}
			else if (config.isStartRpc()) {
				return tryConnect(
						ObjectUtils.defaultIfNull(config.getRpcAddress(), LOCALHOST),
						config.getRpcPort());
			}
			return false;
		}

		private static boolean tryConnect(String host, int port) {
			try (Socket ignored = new Socket(host, port)) {
				return true;
			}
			catch (IOException ex) {
				return false;
			}
		}

	}

}
