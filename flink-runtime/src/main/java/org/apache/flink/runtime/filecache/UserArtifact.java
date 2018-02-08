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

package org.apache.flink.runtime.filecache;

import org.apache.flink.api.common.cache.DistributedCache;
import org.apache.flink.core.fs.Path;
import org.apache.flink.runtime.blob.BlobService;

/**
 * Describes custom user artifact that will be accessible via {@link DistributedCache} from {@link BlobService}.
 */
public class UserArtifact {

	private final Path path;
	private final String name;
	private final boolean isExecutable;

	/**
	 * User artifact.
	 *
	 * @param path target path
	 * @param name name under which it will be accessible
	 * @param isExecutable if the file should be executable
	 */
	public UserArtifact(Path path, String name, boolean isExecutable) {
		this.path = path;
		this.name = name;
		this.isExecutable = isExecutable;
	}

	public Path getPath() {
		return path;
	}

	public String getName() {
		return name;
	}

	public boolean isExecutable() {
		return isExecutable;
	}
}
