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

import org.apache.flink.api.common.JobID;
import org.apache.flink.api.common.cache.DistributedCache.DistributedCacheEntry;
import org.apache.flink.core.fs.FSDataOutputStream;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.core.fs.Path;
import org.apache.flink.runtime.blob.PermanentBlobKey;
import org.apache.flink.runtime.blob.PermanentBlobService;
import org.apache.flink.runtime.util.ExecutorThreadFactory;
import org.apache.flink.util.FileUtils;
import org.apache.flink.util.IOUtils;
import org.apache.flink.util.InstantiationUtil;
import org.apache.flink.util.Preconditions;
import org.apache.flink.util.ShutdownHookUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * The FileCache is used to create the local files for the registered cache files when a task is deployed.
 * The files will be removed when the task is unregistered after a 5 second delay.
 * A given file x will be placed in "{@code <system-tmp-dir>/tmp_<jobID>/}".
 */
public class FileCache {

	static final Logger LOG = LoggerFactory.getLogger(FileCache.class);

	/** cache-wide lock to ensure consistency. copies are not done under this lock. */
	private final Object lock = new Object();

	private final Map<JobID, Map<String, Future<Path>>> entries;

	private final ScheduledExecutorService executorService;

	private final File[] storageDirectories;

	private final Thread shutdownHook;

	private int nextDirectory;

	private final PermanentBlobService blobService;

	// ------------------------------------------------------------------------

	public FileCache(String[] tempDirectories, PermanentBlobService blobService) throws IOException {

		Preconditions.checkNotNull(tempDirectories);

		storageDirectories = new File[tempDirectories.length];

		for (int i = 0; i < tempDirectories.length; i++) {
			String cacheDirName = "flink-dist-cache-" + UUID.randomUUID().toString();
			storageDirectories[i] = new File(tempDirectories[i], cacheDirName);
			String path = storageDirectories[i].getAbsolutePath();

			if (storageDirectories[i].mkdirs()) {
				LOG.info("User file cache uses directory " + path);
			} else {
				LOG.error("User file cache cannot create directory " + path);
				// delete all other directories we created so far
				for (int k = 0; k < i; k++) {
					if (!storageDirectories[k].delete()) {
						LOG.warn("User file cache cannot remove prior directory " +
								storageDirectories[k].getAbsolutePath());
					}
				}
				throw new IOException("File cache cannot create temp storage directory: " + path);
			}
		}

		this.shutdownHook = createShutdownHook(this, LOG);

		this.entries = new HashMap<>();
		this.executorService = Executors.newScheduledThreadPool(10,
				new ExecutorThreadFactory("flink-file-cache"));
		this.blobService = blobService;
	}

	/**
	 * Shuts down the file cache by cancelling all.
	 */
	public void shutdown() {
		synchronized (lock) {
			// first shutdown the thread pool
			ScheduledExecutorService es = this.executorService;
			if (es != null) {
				es.shutdown();
				try {
					es.awaitTermination(5000L, TimeUnit.MILLISECONDS);
				}
				catch (InterruptedException e) {
					// may happen
				}
			}

			entries.clear();

			// clean up the all storage directories
			for (File dir : storageDirectories) {
				try {
					FileUtils.deleteDirectory(dir);
				}
				catch (IOException e) {
					LOG.error("File cache could not properly clean up storage directory.");
				}
			}

			// Remove shutdown hook to prevent resource leaks
			ShutdownHookUtil.removeShutdownHook(shutdownHook, getClass().getSimpleName(), LOG);
		}
	}

	// ------------------------------------------------------------------------

	/**
	 * If the file doesn't exists locally, it will copy the file to the temp directory.
	 *
	 * @param entry The cache entry descriptor (path, executable flag)
	 * @param jobID The ID of the job for which the file is copied.
	 * @return The handle to the task that copies the file.
	 */
	public Future<Path> createTmpFile(String name, DistributedCacheEntry entry, JobID jobID) {
		synchronized (lock) {
			Map<String, Future<Path>> jobEntries = entries.computeIfAbsent(jobID, k -> new HashMap<>());

			// tuple is (ref-count, parent-temp-dir, cached-file-path, copy-process)
			Future<Path> fileEntry = jobEntries.get(name);
			if (fileEntry != null) {
				// file is already in the cache. return a future that
				// immediately returns the file
				return fileEntry;
			} else {
				// need to copy the file

				// create the target path
				File tempDirToUse = new File(storageDirectories[nextDirectory++], jobID.toString());
				if (nextDirectory >= storageDirectories.length) {
					nextDirectory = 0;
				}

				String sourceFile = entry.filePath;
				int posOfSep = sourceFile.lastIndexOf("/");
				if (posOfSep > 0) {
					sourceFile = sourceFile.substring(posOfSep + 1);
				}

				Path target = new Path(tempDirToUse.getAbsolutePath() + "/" + sourceFile);

				// kick off the copying
				Callable<Path> cp = new CopyFromBlobProcess(entry, jobID, blobService, target);
				FutureTask<Path> copyTask = new FutureTask<>(cp);
				executorService.submit(copyTask);

				// store our entry
				jobEntries.put(name, copyTask);

				return copyTask;
			}
		}
	}

	private static Thread createShutdownHook(final FileCache cache, final Logger logger) {

		return ShutdownHookUtil.addShutdownHook(
			cache::shutdown,
			FileCache.class.getSimpleName(),
			logger
		);
	}

	// ------------------------------------------------------------------------
	//  background processes
	// ------------------------------------------------------------------------

	/**
	 * Asynchronous file copy process from blob server.
	 */
	private static class CopyFromBlobProcess implements Callable<Path> {

		private final PermanentBlobKey blobKey;
		private final Path target;
		private final boolean directory;
		private final boolean executable;
		private final JobID jobID;
		private final PermanentBlobService blobService;

		CopyFromBlobProcess(DistributedCacheEntry e, JobID jobID, PermanentBlobService blobService, Path target) {
			try {
				this.executable = e.isExecutable;
				this.directory = e.isZipped;
				this.jobID = jobID;
				this.blobService = blobService;
				this.blobKey = InstantiationUtil.deserializeObject(e.blobKey, Thread.currentThread().getContextClassLoader());
				this.target = target;
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}

		@Override
		public Path call() throws IOException {
			final File file = blobService.getFile(jobID, blobKey);

			if (directory) {
				try (ZipInputStream zis = new ZipInputStream(new FileInputStream(file))) {
					ZipEntry entry;
					while ((entry = zis.getNextEntry()) != null) {
						String fileName = entry.getName();
						Path newFile = new Path(target, fileName);
						if (entry.isDirectory()) {
							target.getFileSystem().mkdirs(newFile);
						} else {
							try (FSDataOutputStream fsDataOutputStream = target.getFileSystem().create(newFile, FileSystem.WriteMode.NO_OVERWRITE)) {
								IOUtils.copyBytes(zis, fsDataOutputStream, false);
							}
							//noinspection ResultOfMethodCallIgnored
							new File(newFile.getPath()).setExecutable(executable);
						}
					}
				}
				Files.delete(file.toPath());
				return target;
			} else {
				//noinspection ResultOfMethodCallIgnored
				file.setExecutable(executable);
				return Path.fromLocalFile(file);
			}

		}
	}

}
