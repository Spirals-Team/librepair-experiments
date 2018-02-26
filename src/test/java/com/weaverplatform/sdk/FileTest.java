package com.weaverplatform.sdk;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.Assert.assertEquals;


public class FileTest {

  @Test
  public void test() throws IOException {

    // Upload
    Weaver w = TestSuite.getInstance();

    String fileName = "fisheye.jpeg";
    InputStream fileStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
    File file = w.uploadFile(fileStream, fileName);
    assertEquals(fileName, file.getName());

    // List
    MinioFile[] list = w.listFiles();
    assertEquals(1, list.length);
    for(MinioFile existing : list) {

      System.out.println(existing.id + ": " + existing.name + " (" + existing.getSize() + ")");

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
