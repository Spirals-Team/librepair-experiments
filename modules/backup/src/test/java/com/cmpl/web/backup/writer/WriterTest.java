package com.cmpl.web.backup.writer;

import java.time.format.DateTimeFormatter;

public class WriterTest extends CommonWriter<EntityTest> {

  public WriterTest() {

  }

  public WriterTest(DateTimeFormatter dateFormatter, DataManipulator<EntityTest> dataManipulator, String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "test";
  }

}
