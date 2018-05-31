package com.cmpl.web.backup.reader;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.core.association_user_role.AssociationUserRole;

public class AssociationUserRoleCSVParser extends CommonParser<AssociationUserRole> {

  public AssociationUserRoleCSVParser(DateTimeFormatter dateFormatter,
      DataManipulator<AssociationUserRole> dataManipulator, String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected AssociationUserRole parseEntity(CSVRecord record) {
    AssociationUserRole associationParsed = new AssociationUserRole();

    List<Field> fieldsToParse = getFields(associationParsed.getClass());

    parseObject(record, associationParsed, fieldsToParse);

    return associationParsed;
  }

  @Override
  public String getParserName() {
    return "associations_user_role";
  }
}
