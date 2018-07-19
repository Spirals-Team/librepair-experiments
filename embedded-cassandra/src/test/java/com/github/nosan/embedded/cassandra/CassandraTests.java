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

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.github.nosan.embedded.cassandra.cql.ClassPathCqlScript;
import com.github.nosan.embedded.cassandra.cql.CqlScriptUtils;

/**
 * Tests for {@link Cassandra}.
 *
 * @author Dmytro Nosan
 */
public class CassandraTests {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private static void start(Cassandra cassandra, Callback callback) throws Exception {
		try {
			cassandra.start();
			callback.run();
		}
		finally {
			cassandra.stop();
		}
	}

	@Test
	public void shouldStartCassandraUsingDefaultConfiguration() throws Exception {
		Cassandra cassandra = new Cassandra();
		start(cassandra, () -> {
			CqlScriptUtils.executeScripts(cassandra.getSession(), new ClassPathCqlScript("init.cql"));
		});
	}

	@Test
	public void shouldNotStartCassandraIfCassandraHasBeenAlreadyStarted()
			throws Exception {
		this.expectedException.expect(IOException.class);
		this.expectedException.expectMessage("Cassandra has already been started");
		Cassandra cassandra = new Cassandra();
		start(cassandra, cassandra::start);
	}

	@Test
	public void shouldBeAbleToRestartCassandra() throws Exception {
		Cassandra cassandra = new Cassandra();
		start(cassandra, () -> {
		});
		start(cassandra, () -> {
		});
	}

	interface Callback {

		void run() throws Exception;

	}

}
