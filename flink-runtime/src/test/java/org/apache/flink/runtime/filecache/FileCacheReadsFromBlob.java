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
import org.apache.flink.api.common.cache.DistributedCache;
import org.apache.flink.core.fs.Path;
import org.apache.flink.runtime.blob.PermanentBlobKey;
import org.apache.flink.runtime.blob.PermanentBlobService;
import org.apache.flink.util.InstantiationUtil;

import org.apache.flink.shaded.guava18.com.google.common.base.Charsets;
import org.apache.flink.shaded.guava18.com.google.common.io.Files;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileCacheReadsFromBlob {

	private static final String testFileContent = "Goethe - Faust: Der Tragoedie erster Teil\n" + "Prolog im Himmel.\n"
		+ "Der Herr. Die himmlischen Heerscharen. Nachher Mephistopheles. Die drei\n" + "Erzengel treten vor.\n"
		+ "RAPHAEL: Die Sonne toent, nach alter Weise, In Brudersphaeren Wettgesang,\n"
		+ "Und ihre vorgeschriebne Reise Vollendet sie mit Donnergang. Ihr Anblick\n"
		+ "gibt den Engeln Staerke, Wenn keiner Sie ergruenden mag; die unbegreiflich\n"
		+ "hohen Werke Sind herrlich wie am ersten Tag.\n"
		+ "GABRIEL: Und schnell und unbegreiflich schnelle Dreht sich umher der Erde\n"
		+ "Pracht; Es wechselt Paradieseshelle Mit tiefer, schauervoller Nacht. Es\n"
		+ "schaeumt das Meer in breiten Fluessen Am tiefen Grund der Felsen auf, Und\n"
		+ "Fels und Meer wird fortgerissen Im ewig schnellem Sphaerenlauf.\n"
		+ "MICHAEL: Und Stuerme brausen um die Wette Vom Meer aufs Land, vom Land\n"
		+ "aufs Meer, und bilden wuetend eine Kette Der tiefsten Wirkung rings umher.\n"
		+ "Da flammt ein blitzendes Verheeren Dem Pfade vor des Donnerschlags. Doch\n"
		+ "deine Boten, Herr, verehren Das sanfte Wandeln deines Tags.\n";

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	private FileCache fileCache;

	@Mock
	private PermanentBlobService blobService;

	@Before
	public void setup() throws IOException {
		try {
			String[] tmpDirectories = new String[]{temporaryFolder.newFolder().getAbsolutePath()};
			fileCache = new FileCache(tmpDirectories, blobService);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Cannot create FileCache: " + e.getMessage());
		}
	}

	@After
	public void shutdown() {
		try {
			fileCache.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
			fail("FileCache shutdown failed: " + e.getMessage());
		}
	}

	@Test
	public void testFileDownloadedFromBlob() {
		try {
			JobID jobID = new JobID();

			final PermanentBlobKey permanentBlobKey = new PermanentBlobKey();
			when(blobService.getFile(jobID, permanentBlobKey)).thenAnswer(inv -> {
					File f = temporaryFolder.newFile("cacheFile");
					try {
						Files.write(testFileContent, f, Charsets.UTF_8);
					} catch (Exception e) {
						e.printStackTrace();
						fail("Error initializing the test: " + e.getMessage());
					}
					return f;
				}
			);

			final String fileName = "test_file";
			// copy / create the file
			final DistributedCache.DistributedCacheEntry entry = new DistributedCache.DistributedCacheEntry(
				fileName,
				false,
				true,
				InstantiationUtil.serializeObject(permanentBlobKey));
			Future<Path> copyResult = fileCache.createTmpFile(fileName, entry, jobID);

			final Path dstPath = copyResult.get();
			assertTrue(dstPath.getFileSystem().exists(dstPath));
			assertEquals(fileName, dstPath.getName());

		}
		catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
