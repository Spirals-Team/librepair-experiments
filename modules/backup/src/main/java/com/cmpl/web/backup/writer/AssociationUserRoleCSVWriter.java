package com.cmpl.web.backup.writer;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.models.Responsibility;

public class AssociationUserRoleCSVWriter extends CommonWriter<Responsibility> {

  public AssociationUserRoleCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<Responsibility> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "associations_user_role";
  }
}
