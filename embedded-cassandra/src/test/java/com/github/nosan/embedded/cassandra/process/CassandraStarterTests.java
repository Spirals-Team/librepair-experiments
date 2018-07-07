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

import com.datastax.driver.core.Cluster;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.nosan.embedded.cassandra.Config;
import com.github.nosan.embedded.cassandra.ExecutableConfig;
import com.github.nosan.embedded.cassandra.support.ExecutableConfigBuilder;
import com.github.nosan.embedded.cassandra.support.RuntimeConfigBuilder;

/**
 * Tests for {@link CassandraStarter}.
 *
 * @author Dmytro Nosan
 */
public class CassandraStarterTests {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private static final Logger log = LoggerFactory
			.getLogger(CassandraStarterTests.class);

	private static Cluster cluster(Config config) {
		return Cluster.builder().addContactPoint(config.getListenAddress())
				.withPort(config.getNativeTransportPort()).build();
	}

	@Test
	public void useNativeTransport() throws Exception {
		ExecutableConfig executableConfig = new ExecutableConfigBuilder().build();
		start(executableConfig, () -> {
			try (Cluster cluster = cluster(executableConfig.getConfig())) {
				cluster.connect().execute("CREATE KEYSPACE IF NOT EXISTS test  "
						+ "WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 }");
			}
		});
	}

	@Test
	public void restart() throws Exception {
		ExecutableConfig executableConfig = new ExecutableConfigBuilder().build();
		Config config = new Config();
		config.setNativeTransportPort(9042);
		executableConfig.setConfig(config);
		start(executableConfig);
		start(executableConfig);
	}

	@Test
	public void multiplyInstances() throws Exception {
		start(new ExecutableConfigBuilder().build(),
				() -> start(new ExecutableConfigBuilder().build()));
	}

	@Test
	public void invalidConfig() throws Exception {
		this.expectedException.expect(IOException.class);
		this.expectedException.expectMessage("Missing required directive CommitLogSync");
		ExecutableConfig executableConfig = new ExecutableConfigBuilder().build();
		executableConfig.getConfig().setCommitlogSync(null);
		start(executableConfig);
	}

	@Test
	public void timeout() throws Exception {
		this.expectedException.expect(IOException.class);
		this.expectedException.expectMessage(
				"Could not start process. Please increase your startup timeout");
		ExecutableConfig executableConfig = new ExecutableConfigBuilder()
				.timeout(Duration.ofSeconds(1)).build();
		start(executableConfig);
	}

	@Test
	public void shouldUseRpcTransport() throws Exception {
		ExecutableConfig executableConfig = new ExecutableConfigBuilder().build();
		Config config = executableConfig.getConfig();
		config.setStartNativeTransport(false);
		config.setStartRpc(true);
		start(executableConfig);
	}

	@Test
	public void shouldWorkWithDisableTransport() throws Exception {
		ExecutableConfig executableConfig = new ExecutableConfigBuilder().build();
		Config config = executableConfig.getConfig();
		config.setStartNativeTransport(false);
		config.setStartRpc(false);

		start(executableConfig);

	}

	private static void start(ExecutableConfig executableConfig) throws Exception {
		start(executableConfig, () -> {
		});
	}

	private static void start(ExecutableConfig executableConfig, Callback callback)
			throws Exception {
		CassandraExecutable executable = new CassandraStarter(
				new RuntimeConfigBuilder(log).build()).prepare(executableConfig);
		try {
			executable.start();
			callback.run();
		}
		finally {
			if (executable != null) {
				executable.stop();
			}
		}
	}

	interface Callback {

		void run() throws Exception;

	}

}
