package com.weaverplatform.sdk;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.Assert.assertEquals;


public class WeaverFileTest {

  @Test
  public void test() throws IOException {

    Weaver w = TestSuite.getInstance();

    // Wipe all
    MinioFile[] list = w.listFiles();
    for(MinioFile existing : list) {
      w.deleteFile(existing.getId());
    }

    // Upload
    String fileName = "fisheye.jpeg";
    InputStream fileStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
    WeaverFile file = w.uploadFile(fileStream, fileName);
    assertEquals(fileName, file.getName());

    // List
    list = w.listFiles();
    assertEquals(1, list.length);
    for(MinioFile existing : list) {

      // Download
      InputStream stream = w.downloadFile(existing.getId());
      Files.copy(stream, Paths.get("/tmp/fisheye.jpeg"), StandardCopyOption.REPLACE_EXISTING);

      // Delete
      w.deleteFile(existing.getId());
    }

    list = w.listFiles();
    assertEquals(0, list.length);
  }
}
