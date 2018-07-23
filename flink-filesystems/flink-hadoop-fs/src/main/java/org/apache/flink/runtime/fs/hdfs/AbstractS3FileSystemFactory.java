/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.runtime.fs.hdfs;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.core.fs.FileSystemFactory;
import org.apache.flink.runtime.util.HadoopUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/** Base class for S3 file system factories. */
public abstract class AbstractS3FileSystemFactory  implements FileSystemFactory {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractS3FileSystemFactory.class);

	/** Name of this factory for logging. */
	private final String name;

	/** The prefixes that Flink adds to the Hadoop config under 'fs.s3a.'. */
	private final String[] flinkConfigPrefixes;

	/** Keys that are replaced (after prefix replacement, to give a more uniform experience
	 * across different file system implementations. */
	private final String[][] mirroredConfigKeys;

	/** Hadoop config prefix to replace Flink prefix. */
	private final String hadoopConfigPrefix;

	/** Flink's configuration object. */
	private Configuration flinkConfig;

	/** Hadoop's configuration for the file systems, lazily initialized. */
	private org.apache.hadoop.conf.Configuration hadoopConfig;

	protected AbstractS3FileSystemFactory(
		String name, String[] flinkConfigPrefixes, String[][] mirroredConfigKeys, String hadoopConfigPrefix) {
		this.name = name;
		this.flinkConfigPrefixes = flinkConfigPrefixes;
		this.mirroredConfigKeys = mirroredConfigKeys;
		this.hadoopConfigPrefix = hadoopConfigPrefix;
	}

	@Override
	public String getScheme() {
		return "s3";
	}

	@Override
	public void configure(Configuration config) {
		flinkConfig = config;
		hadoopConfig = null;
	}

	@Override
	public FileSystem create(URI fsUri) throws IOException {
		LOG.debug("Creating S3 file system (backed by " + name + ")");

		try {
			org.apache.hadoop.conf.Configuration hadoopConfig = loadHadoopConfig(this.hadoopConfig);
			this.hadoopConfig = hadoopConfig;
			final org.apache.hadoop.fs.FileSystem fs = createHadoopFileSystem();
			fs.initialize(getInitURI(fsUri, hadoopConfig), hadoopConfig);
			return new HadoopFileSystem(fs);
		}
		catch (IOException e) {
			throw e;
		}
		catch (Exception e) {
			throw new IOException(e.getMessage(), e);
		}
	}

	/** get the loaded Hadoop config (or fall back to one loaded from the classpath). */
	private org.apache.hadoop.conf.Configuration loadHadoopConfig(org.apache.hadoop.conf.Configuration hadoopConfig) {
		if (hadoopConfig == null) {
			if (flinkConfig != null) {
				return loadHadoopConfigFromFlink();
			}
			else {
				LOG.warn("The factory has not been configured prior to loading the S3 file system."
					+ " Using Hadoop configuration from the classpath.");
				return new org.apache.hadoop.conf.Configuration();
			}
		}
		return hadoopConfig;
	}

	private org.apache.hadoop.conf.Configuration loadHadoopConfigFromFlink() {
		LOG.debug("Loading Hadoop configuration for " + name);
		org.apache.hadoop.conf.Configuration hadoopConfig = HadoopUtils.getHadoopConfiguration(flinkConfig);

		// add additional config entries from the Flink config to the Presto Hadoop config
		for (String key : flinkConfig.keySet()) {
			for (String prefix : flinkConfigPrefixes) {
				if (key.startsWith(prefix)) {
					String value = flinkConfig.getString(key, null);
					String newKey = hadoopConfigPrefix + key.substring(prefix.length());
					hadoopConfig.set(newKey, flinkConfig.getString(key, null));

					LOG.debug("Adding Flink config entry for {} as {}={} to Hadoop config for {}",
						key, newKey, value, name);
				}
			}
		}

		// mirror certain keys to make use more uniform across s3 implementations
		// with different keys
		for (String[] mirrored : mirroredConfigKeys) {
			String value = hadoopConfig.get(mirrored[0], null);
			if (value != null) {
				hadoopConfig.set(mirrored[1], value);
			}
		}

		return hadoopConfig;
	}

	protected abstract org.apache.hadoop.fs.FileSystem createHadoopFileSystem();

	protected abstract URI getInitURI(URI fsUri, org.apache.hadoop.conf.Configuration hadoopConfig) throws URISyntaxException;
}

