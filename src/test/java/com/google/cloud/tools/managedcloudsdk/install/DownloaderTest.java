/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.tools.managedcloudsdk.install;

import com.google.cloud.tools.managedcloudsdk.ProgressListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DownloaderTest {

  @Rule public TemporaryFolder tmp = new TemporaryFolder();
  @Mock private ProgressListener mockProgressListener;

  private Path createTestRemoteResource(long sizeInBytes) throws IOException {

    Path testFile = tmp.newFile().toPath();
    try (BufferedWriter writer = Files.newBufferedWriter(testFile, StandardCharsets.UTF_8)) {
      for (long i = 0; i < sizeInBytes; i++) {
        writer.write('a');
      }
    }
    return testFile;
  }

  @Test
  public void testDownload_createsNewDirectory() throws IOException, InterruptedException {
    Path destination = tmp.getRoot().toPath().resolve("dir-to-create").resolve("destination-file");
    Path testSourceFile = createTestRemoteResource(1);
    URL fakeRemoteResource = testSourceFile.toUri().toURL();

    Downloader downloader =
        new Downloader(fakeRemoteResource, destination, "user agent", mockProgressListener);

    downloader.download();
    Assert.assertTrue(Files.exists(destination));
  }

  @Test
  public void testDownload_worksWithNullProgressListener()
      throws IOException, InterruptedException {

    Path destination = tmp.getRoot().toPath().resolve("destination-file");
    Path testSourceFile = createTestRemoteResource(1);
    URL fakeRemoteResource = testSourceFile.toUri().toURL();

    Downloader downloader =
        new Downloader(fakeRemoteResource, destination, "Dummy User Agent", mockProgressListener);

    downloader.download();
    Assert.assertTrue(Files.exists(destination));
  }

  @Test
  public void testDownload() throws IOException, InterruptedException {
    Path destination = tmp.getRoot().toPath().resolve("destination-file");
    long testFileSize = Downloader.BUFFER_SIZE * 10 + 1;
    Path testSourceFile = createTestRemoteResource(testFileSize);
    URL fakeRemoteResource = testSourceFile.toUri().toURL();

    Downloader downloader =
        new Downloader(fakeRemoteResource, destination, "Dummy User Agent", mockProgressListener);

    downloader.download();
    Assert.assertTrue(Files.exists(destination));
    Assert.assertArrayEquals(Files.readAllBytes(destination), Files.readAllBytes(testSourceFile));

    ProgressVerifier.verifyProgress(mockProgressListener, "Downloading 0.08 MB");
  }

  @Test
  public void testGetDownloadStatus() {
    Assert.assertEquals("Downloading 0.08 MB", Downloader.getDownloadStatus(81921, Locale.ENGLISH));
    Assert.assertEquals("Downloading 0,08 MB", Downloader.getDownloadStatus(81921, Locale.GERMAN));

    Assert.assertEquals(
        "Downloading 1,000.00 MB", Downloader.getDownloadStatus(1048576000, Locale.ENGLISH));
    Assert.assertEquals(
        "Downloading 1.000,00 MB", Downloader.getDownloadStatus(1048576000, Locale.GERMAN));
  }

  @Test
  public void testDownload_userAgentSet() throws IOException {
    Path destination = tmp.getRoot().toPath().resolve("destination-file");

    final URLConnection mockConnection = Mockito.mock(URLConnection.class);
    URLStreamHandler testHandler =
        new URLStreamHandler() {
          @Override
          protected URLConnection openConnection(URL url) throws IOException {
            return mockConnection;
          }
        };

    // create a URL with a custom streamHandler so we can get our mock connection
    URL testUrl = new URL("", "", 80, "", testHandler);
    Downloader downloader =
        new Downloader(testUrl, destination, "test-user-agent", mockProgressListener);

    try {
      downloader.download();
    } catch (Exception ex) {
      // ignore, we're only looking for user agent being set
    }
    Mockito.verify(mockConnection).setRequestProperty("User-Agent", "test-user-agent");
  }

  @Test
  public void testDownload_failIfExists() throws IOException, InterruptedException {
    Path destination = tmp.getRoot().toPath().resolve("destination-file");
    Files.createFile(destination);

    URL testUrl = new URL("http://www.example.com");
    Downloader downloader =
        new Downloader(testUrl, destination, "user agent", mockProgressListener);

    try {
      downloader.download();
      Assert.fail("FileAlreadyExistsException expected but not thrown.");
    } catch (FileAlreadyExistsException ex) {
      Assert.assertEquals(ex.getMessage(), destination.toString());
    }
  }

  @Test
  public void testDownload_interruptTriggersCleanup()
      throws IOException, InterruptedException, ExecutionException {
    Path destination = tmp.getRoot().toPath().resolve("destination-file");
    long testFileSize = Downloader.BUFFER_SIZE * 10 + 1;
    Path testSourceFile = createTestRemoteResource(testFileSize);
    URL fakeRemoteResource = testSourceFile.toUri().toURL();

    // Start a new thread for this test to avoid mucking with Thread state when
    // junit reuses threads.
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Future<Void> testThreadToInterrupt =
        executorService.submit(
            new Callable<Void>() {
              @Override
              public Void call() throws Exception {
                Downloader downloader =
                    new Downloader(
                        fakeRemoteResource, destination, "user agent", mockProgressListener);
                Thread.currentThread().interrupt();
                try {
                  downloader.download();
                  Assert.fail("InterruptedException expected but not thrown.");
                } catch (InterruptedException ex) {
                  Assert.assertEquals("Download was interrupted", ex.getMessage());
                }
                return null;
              }
            });
    executorService.shutdown();
    testThreadToInterrupt.get();

    Assert.assertFalse(Files.exists(destination));
    Mockito.verify(mockProgressListener, Mockito.never()).update(100);
  }
}
