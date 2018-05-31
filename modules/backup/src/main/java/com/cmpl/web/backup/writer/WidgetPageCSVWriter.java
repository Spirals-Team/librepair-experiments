package com.cmpl.web.backup.writer;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.widget.WidgetPage;

public class WidgetPageCSVWriter extends CommonWriter<WidgetPage> {

  public WidgetPageCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<WidgetPage> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "widget_pages";
  }
}
