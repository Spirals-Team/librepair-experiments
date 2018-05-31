package com.cmpl.web.backup.writer;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.association_user_role.AssociationUserRole;

public class AssociationUserRoleCSVWriter extends CommonWriter<AssociationUserRole> {

  public AssociationUserRoleCSVWriter(DateTimeFormatter dateFormatter,
      DataManipulator<AssociationUserRole> dataManipulator, String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "associations_user_role";
  }
}
