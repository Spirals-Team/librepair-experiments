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

package com.github.nosan.embedded.cassandra.config;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.flapdoodle.embed.process.builder.AbstractBuilder;
import de.flapdoodle.embed.process.builder.IProperty;
import de.flapdoodle.embed.process.builder.TypedProperty;
import de.flapdoodle.embed.process.runtime.Network;

import com.github.nosan.embedded.cassandra.customizer.FileCustomizer;
import com.github.nosan.embedded.cassandra.customizer.JVMOptionsCustomizer;
import com.github.nosan.embedded.cassandra.customizer.JmxPortCustomizer;

/**
 * Simple builder for building {@link ExecutableConfig}.
 *
 * @author Dmytro Nosan
 */
public class ExecutableConfigBuilder extends AbstractBuilder<ExecutableConfig> {

	private static final TypedProperty<Config> CONFIG = TypedProperty.with("Config",
			Config.class);

	private static final TypedProperty<Duration> TIMEOUT = TypedProperty.with("Timeout",
			Duration.class);

	private static final TypedProperty<Version> VERSION = TypedProperty.with("Version",
			Version.class);

	private static final TypedProperty<FileCustomizer[]> FILE_CUSTOMIZERS = TypedProperty
			.with("FileCustomizers", FileCustomizer[].class);

	private static final TypedProperty<Integer> JMX_PORT = TypedProperty.with("JMXPort",
			Integer.class);

	private static final TypedProperty<String[]> JVM_OPTIONS = TypedProperty
			.with("JvmOptions", String[].class);

	private static final TypedProperty<Boolean> RANDOM_PORTS = TypedProperty
			.with("RandomPorts", Boolean.class);

	/**
	 * Configure builder with default settings. JMX is disabled by default.
	 */
	public ExecutableConfigBuilder() {
		config().overwriteDefault(new Config());
		timeout().overwriteDefault(Duration.ofMinutes(1));
		version().overwriteDefault(Version.LATEST);
		jmxPort().overwriteDefault(7199);
		jvmOptions().overwriteDefault(new String[0]);
		useRandomPorts().overwriteDefault(false);
		fileCustomizers().overwriteDefault(new FileCustomizer[0]);
	}

	/**
	 * Sets Cassandra's JMX port.
	 * @param jmxPort JMX port.
	 * @return current builder.
	 * @see JmxPortCustomizer
	 */
	public ExecutableConfigBuilder jmxPort(int jmxPort) {
		jmxPort().overwriteDefault(jmxPort);
		return this;
	}

	/**
	 * Sets Cassandra's JVM Options.
	 * @param jvmOptions JVM Options.
	 * @return current builder
	 * @see JVMOptionsCustomizer
	 */
	public ExecutableConfigBuilder jvmOptions(String... jvmOptions) {
		jvmOptions().set(jvmOptions);
		return this;
	}

	/**
	 * Sets the cassandra version.
	 * @param version cassandra version.
	 * @return current builder
	 */
	public ExecutableConfigBuilder version(Version version) {
		version().set(version);
		return this;
	}

	/**
	 * Sets the cassandra startup timeout.
	 * @param timeout cassandra startup timeout.
	 * @return current builder.
	 */
	public ExecutableConfigBuilder timeout(Duration timeout) {
		timeout().set(timeout);
		return this;
	}

	/**
	 * Sets the cassandra config.
	 * @param config cassandra config.
	 * @return current builder.
	 */
	public ExecutableConfigBuilder config(Config config) {
		config().set(config);
		return this;
	}

	/**
	 * Sets the cassandra file customizers.
	 * @param fileCustomizers {@link FileCustomizer} file customizers.
	 * @return current builder.
	 * @see FileCustomizer
	 */
	public ExecutableConfigBuilder fileCustomizers(FileCustomizer... fileCustomizers) {
		fileCustomizers().set(fileCustomizers);
		return this;
	}

	/**
	 * Override {@link Config} and JMX with random ports.
	 * @param useRandomPorts use random ports.
	 * @return current builder.
	 */
	public ExecutableConfigBuilder useRandomPorts(boolean useRandomPorts) {
		useRandomPorts().set(useRandomPorts);
		return this;
	}

	@Override
	public ExecutableConfig build() {
		int jmxPort = jmxPort().get();

		Config config = config().get();

		if (useRandomPorts().get()) {
			config.setSslStoragePort(0);
			config.setStoragePort(0);
			config.setNativeTransportPort(0);
			config.setRpcPort(0);
			if (config.getNativeTransportPortSsl() != null) {
				config.setNativeTransportPortSsl(0);
			}
			jmxPort = 0;
		}

		if (config.getSslStoragePort() == 0) {
			config.setSslStoragePort(getRandomPort());
		}
		if (config.getStoragePort() == 0) {
			config.setStoragePort(getRandomPort());
		}
		if (config.getNativeTransportPort() == 0) {
			config.setNativeTransportPort(getRandomPort());
		}
		if (config.getRpcPort() == 0) {
			config.setRpcPort(getRandomPort());
		}
		if (config.getNativeTransportPortSsl() != null
				&& config.getNativeTransportPortSsl() == 0) {
			config.setNativeTransportPortSsl(getRandomPort());
		}

		if (jmxPort == 0) {
			jmxPort = getRandomPort();
		}

		return new ImmutableExecutableConfig(config, timeout().get(), version().get(),
				jmxPort, Arrays.asList(fileCustomizers().get()),
				Arrays.asList(jvmOptions().get()));
	}

	/**
	 * Retrieves file customizer property.
	 * @return file customizer {@link IProperty}.
	 */
	protected IProperty<FileCustomizer[]> fileCustomizers() {
		return property(FILE_CUSTOMIZERS);
	}

	/**
	 * Retrieves random ports property.
	 * @return random ports {@link IProperty}.
	 */
	protected IProperty<Boolean> useRandomPorts() {
		return property(RANDOM_PORTS);
	}

	/**
	 * Retrieves version property.
	 * @return version {@link IProperty}.
	 */
	protected IProperty<Version> version() {
		return property(VERSION);
	}

	/**
	 * Retrieves timeout property.
	 * @return timeout {@link IProperty}.
	 */
	protected IProperty<Duration> timeout() {
		return property(TIMEOUT);
	}

	/**
	 * Retrieves JMX port property.
	 * @return JMX port {@link IProperty}.
	 */
	protected IProperty<Integer> jmxPort() {
		return property(JMX_PORT);
	}

	/**
	 * Retrieves config property.
	 * @return config {@link IProperty}.
	 */
	protected IProperty<Config> config() {
		return property(CONFIG);
	}

	/**
	 * Retrieves jvm options property.
	 * @return jvm options {@link IProperty}.
	 */
	protected IProperty<String[]> jvmOptions() {
		return property(JVM_OPTIONS);
	}

	private int getRandomPort() {
		try {
			return Network.getFreeServerPort();
		}
		catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
	}

	private static final class ImmutableExecutableConfig implements ExecutableConfig {

		private final Config config;

		private final Duration timeout;

		private final Version version;

		private final int jmxPort;

		private final List<FileCustomizer> fileCustomizers;

		private final List<String> jvmOptions;

		ImmutableExecutableConfig(Config config, Duration timeout, Version version,
				int jmxPort, List<FileCustomizer> fileCustomizers,
				List<String> jvmOptions) {
			this.config = config;
			this.timeout = timeout;
			this.version = version;
			this.jmxPort = jmxPort;
			this.fileCustomizers = Collections.unmodifiableList(fileCustomizers);
			this.jvmOptions = Collections.unmodifiableList(jvmOptions);
		}

		@Override
		public Config getConfig() {
			return this.config;
		}

		@Override
		public Duration getTimeout() {
			return this.timeout;
		}

		@Override
		public Version getVersion() {
			return this.version;
		}

		@Override
		public int getJmxPort() {
			return this.jmxPort;
		}

		@Override
		public List<String> getJvmOptions() {
			return this.jvmOptions;
		}

		@Override
		public List<FileCustomizer> getFileCustomizers() {
			return this.fileCustomizers;
		}

	}

}
