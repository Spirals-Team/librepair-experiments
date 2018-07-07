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
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.config.io.ProcessOutput;
import de.flapdoodle.embed.process.io.IStreamProcessor;
import de.flapdoodle.embed.process.io.LogWatchStreamProcessor;
import de.flapdoodle.embed.process.io.Processors;
import de.flapdoodle.embed.process.io.StreamToLineProcessor;
import de.flapdoodle.embed.process.runtime.IStopable;
import de.flapdoodle.embed.process.runtime.ProcessControl;
import org.apache.commons.lang3.ObjectUtils;

import com.github.nosan.embedded.cassandra.Config;
import com.github.nosan.embedded.cassandra.ExecutableConfig;

/**
 * A basic implementation of Cassandra Process.
 *
 * @author Dmytro Nosan
 * @see ProcessStarter
 * @see ProcessStopper
 */
public final class CassandraProcess implements IStopable {

	private ProcessControl processControl;

	private final ProcessContext context;

	private final ProcessStarter starter = new ProcessStarter();

	private final ProcessStopper stopper = new ProcessStopper();

	CassandraProcess(ProcessContext context) {
		this.context = Objects.requireNonNull(context,
				"Process Context must not be null");
	}

	public synchronized void start() throws IOException {
		for (ProcessContextCustomizer customizer : getCustomizers()) {
			customizer.customize(this.context);
		}
		this.processControl = this.starter.start(this.context);
		IRuntimeConfig runtimeConfig = this.context.getRuntimeConfig();
		ExecutableConfig executableConfig = this.context.getExecutableConfig();
		ProcessOutput processOutput = runtimeConfig.getProcessOutput();
		ProcessLogWatch logWatch = new ProcessLogWatch(executableConfig.getConfig(),
				processOutput.getOutput());
		ProcessNetworkWatch networkWatch = new ProcessNetworkWatch(
				executableConfig.getConfig());

		Processors.connect(this.processControl.getReader(),
				StreamToLineProcessor.wrap(logWatch));
		Processors.connect(this.processControl.getError(),
				StreamToLineProcessor.wrap(processOutput.getError()));

		try {
			await(executableConfig.getTimeout(), () -> {
				String failureFound = logWatch.getFailureFound();
				if (failureFound != null) {
					throw new IOException("Could not start process. " + failureFound);
				}
				if (networkWatch.isNativeOrRpcEnabled()) {
					return logWatch.isInitWithSuccess() && networkWatch.connect();
				}
				else {
					return logWatch.isInitWithSuccess();
				}
			});
		}
		catch (Exception ex) {
			if (ex instanceof IOException) {
				throw (IOException) ex;
			}
			throw new IOException(ex);
		}
	}

	@Override
	public synchronized void stop() {
		if (this.processControl != null) {
			this.stopper.stop(this.context, this.processControl);
		}
	}

	@Override
	public boolean isRegisteredJobKiller() {
		return false;
	}

	private List<ProcessContextCustomizer> getCustomizers() {
		List<ProcessContextCustomizer> customizers = new ArrayList<>();
		FileCustomizers fileCustomizers = new FileCustomizers();
		fileCustomizers.addCustomizer(new JavaCompatibilityFileCustomizer());
		fileCustomizers.addCustomizer(new JmxPortFileCustomizer());
		fileCustomizers.addCustomizer(new JVMOptionsFileCustomizer());
		fileCustomizers.addCustomizer(new ConfigFileCustomizer());
		customizers.add(new RandomPortCustomizer());
		customizers.add(fileCustomizers);
		return customizers;
	}

	private void await(Duration timeout, Callable<Boolean> callable) throws Exception {
		long startTime = System.nanoTime();
		long rem = timeout.toNanos();
		do {
			if (callable.call()) {
				return;
			}
			if (rem > 0) {
				Thread.sleep(Math.min(TimeUnit.NANOSECONDS.toMillis(rem) + 1, 100));
			}
			rem = timeout.toNanos() - (System.nanoTime() - startTime);
		}
		while (rem > 0);
		throw new IOException(
				"Could not start process. Please increase your startup timeout");
	}

	/**
	 * Utility class for waiting while cassandra will be ready.
	 */
	private static final class ProcessNetworkWatch {

		private Config config;

		ProcessNetworkWatch(Config config) {
			this.config = config;
		}

		boolean connect() {
			if (this.config.isStartNativeTransport()) {
				return tryConnect(ObjectUtils
						.defaultIfNull(this.config.getListenAddress(), "localhost"),
						this.config.getNativeTransportPort());
			}
			else if (this.config.isStartRpc()) {
				return tryConnect(ObjectUtils.defaultIfNull(this.config.getRpcAddress(),
						"localhost"), this.config.getRpcPort());
			}
			return false;
		}

		boolean isNativeOrRpcEnabled() {
			return this.config.isStartNativeTransport() || this.config.isStartRpc();
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

	/**
	 * Utility class for watching cassandra log's.
	 */
	private static final class ProcessLogWatch extends LogWatchStreamProcessor {

		ProcessLogWatch(Config config, IStreamProcessor output) {
			super(getSuccess(config), getFailures(), output);
		}

		@Override
		public void onProcessed() {

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
					"Error occurred during initialization"));
		}

	}

}
