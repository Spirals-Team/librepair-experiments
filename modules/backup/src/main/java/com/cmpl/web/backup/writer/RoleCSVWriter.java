package com.cmpl.web.backup.writer;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.role.Role;

public class RoleCSVWriter extends CommonWriter<Role> {

  public RoleCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<Role> dataManipulator, String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "roles";
  }
}
