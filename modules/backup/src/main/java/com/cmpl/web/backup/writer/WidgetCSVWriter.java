package com.cmpl.web.backup.writer;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.widget.Widget;

public class WidgetCSVWriter extends CommonWriter<Widget> {

  public WidgetCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<Widget> dataManipulator, String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "widgets";
  }
}
