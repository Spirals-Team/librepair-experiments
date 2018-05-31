package com.cmpl.web.backup.writer;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.role.Privilege;

public class PrivilegeCSVWriter extends CommonWriter<Privilege> {

  public PrivilegeCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<Privilege> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "privileges";
  }
}
