package com.cmpl.web.backup.writer;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.menu.Menu;

public class MenuCSVWriter extends CommonWriter<Menu> {

  public MenuCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<Menu> dataManipulator, String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "menu";
  }

}
