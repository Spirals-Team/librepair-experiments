package com.cmpl.web.backup.writer;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.page.Page;

public class PageCSVWriter extends CommonWriter<Page> {

  public PageCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<Page> dataManipulator, String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "pages";
  }

}
