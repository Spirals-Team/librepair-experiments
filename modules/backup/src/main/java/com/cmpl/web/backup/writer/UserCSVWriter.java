package com.cmpl.web.backup.writer;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.user.User;

public class UserCSVWriter extends CommonWriter<User> {

  public UserCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<User> dataManipulator, String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "users";
  }
}
