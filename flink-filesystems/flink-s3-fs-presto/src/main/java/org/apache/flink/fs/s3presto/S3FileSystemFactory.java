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

package org.apache.flink.fs.s3presto;

import org.apache.flink.runtime.fs.hdfs.AbstractS3FileSystemFactory;

import com.facebook.presto.hive.PrestoS3FileSystem;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Simple factory for the S3 file system.
 */
public class S3FileSystemFactory extends AbstractS3FileSystemFactory {
	private static final String[] FLINK_CONFIG_PREFIXES = { "s3.", "presto.s3." };

	private static final String[][] MIRRORED_CONFIG_KEYS = {
			{ "presto.s3.access.key", "presto.s3.access-key" },
			{ "presto.s3.secret.key", "presto.s3.secret-key" }
	};

	protected S3FileSystemFactory() {
		super("Presto S3 File System", FLINK_CONFIG_PREFIXES, MIRRORED_CONFIG_KEYS, "presto.s3.");
	}

	@Override
	protected org.apache.hadoop.fs.FileSystem createHadoopFileSystem() {
		return new PrestoS3FileSystem();
	}

	@Override
	protected URI getInitURI(URI fsUri, org.apache.hadoop.conf.Configuration hadoopConfig) throws URISyntaxException {
		final String scheme = fsUri.getScheme();
		final String authority = fsUri.getAuthority();
		final URI initUri;

		if (scheme == null && authority == null) {
			initUri = new URI("s3://s3.amazonaws.com");
		}
		else if (scheme != null && authority == null) {
			initUri = new URI(scheme + "://s3.amazonaws.com");
		}
		else {
			initUri = fsUri;
		}
		return initUri;
	}
}
