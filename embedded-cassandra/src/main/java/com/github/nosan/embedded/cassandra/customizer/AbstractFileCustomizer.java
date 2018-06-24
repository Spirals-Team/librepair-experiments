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

package com.github.nosan.embedded.cassandra.customizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import de.flapdoodle.embed.process.distribution.Distribution;

/**
 * A basic {@link FileCustomizer}.
 *
 * @author Dmytro Nosan
 */
public abstract class AbstractFileCustomizer implements FileCustomizer {

	@Override
	public final void customize(File file, Distribution distribution) throws IOException {
		if (isMatch(file, distribution)) {
			List<String> lines = new ArrayList<>();
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String line;
				while ((line = reader.readLine()) != null) {
					lines.add(process(line));
				}
			}
			try (PrintWriter fileWriter = new PrintWriter(new FileWriter(file))) {
				for (String line : lines) {
					fileWriter.println(line);
				}
			}
		}
	}

	/**
	 * Determine if the specified file matches or not.
	 * @param file Source file.
	 * @param distribution {@link Distribution}.
	 * @return Whether source file is match or not.
	 */
	protected abstract boolean isMatch(File file, Distribution distribution);

	/**
	 * Process the provided line.
	 * @param line Source line.
	 * @return processed line.
	 */
	protected abstract String process(String line);

}
