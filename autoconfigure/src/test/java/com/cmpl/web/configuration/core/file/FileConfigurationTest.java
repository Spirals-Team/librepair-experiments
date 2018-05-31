package com.cmpl.web.configuration.core.file;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.file.FileServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class FileConfigurationTest {

  @Mock
  private ContextHolder contextHolder;

  @Spy
  FileConfiguration configuration;

  @Test
  public void testFileService() throws Exception {
    FileService result = configuration.fileService(contextHolder);

    Assert.assertEquals(FileServiceImpl.class, result.getClass());
  }

}
