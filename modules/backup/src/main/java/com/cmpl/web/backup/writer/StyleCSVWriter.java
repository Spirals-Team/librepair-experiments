package com.cmpl.web.backup.writer;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.style.Style;

public class StyleCSVWriter extends CommonWriter<Style> {

  public StyleCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<Style> dataManipulator, String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "styles";
  }

}
