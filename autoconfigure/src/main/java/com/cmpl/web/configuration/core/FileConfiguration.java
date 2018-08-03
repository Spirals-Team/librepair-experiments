package com.cmpl.web.configuration.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.file.FileServiceImpl;

@Configuration
public class FileConfiguration {

  @Bean
  public FileService fileService(ContextHolder contextHolder) {
    return new FileServiceImpl(contextHolder);
  }

}
