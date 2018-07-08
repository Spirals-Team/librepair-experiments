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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.SystemUtils;

/**
 * Simple class for Cassandra's JVM Options.
 *
 * @author Dmytro Nosan
 */
public final class JvmOptions {

	private final List<String> options = new ArrayList<>();

	private final Mode mode;

	public JvmOptions(Mode mode, String... options) {
		this.options.addAll(Arrays
				.asList(Objects.requireNonNull(options, "Options must not be null")));
		this.mode = Objects.requireNonNull(mode, "Mode must not be null");
	}

	/**
	 * Retrieves Cassandra's JVM Options.
	 * @return JVM Options.
	 */
	public List<String> getOptions() {
		return this.options;
	}

	/**
	 * Retrieves jvm.options file manipulations mode.
	 * @return {@link Mode} mode.
	 */
	public Mode getMode() {
		return this.mode;
	}

	/**
	 * Mode how to work with jvm.options file.
	 * @author Dmytro Nosan
	 */
	public enum Mode {

		/**
		 * Existing `jvm.options` will be replaced.
		 */
		REPLACE,
		/**
		 * Additional JVM Options will be added at the end of `jvm.options`.
		 */
		ADD

	}

	/**
	 * Detect default jvm options.
	 * @return default jvm options.
	 */
	public static JvmOptions detect() {
		if (SystemUtils.IS_JAVA_1_8) {
			return new JvmOptions(Mode.ADD, "-XX:-UseNUMA", "-Xmx256m", "-Xms256m");
		}
		return new JvmOptions(Mode.ADD);
	}

}
