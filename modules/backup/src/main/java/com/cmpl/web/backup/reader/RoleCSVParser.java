package com.cmpl.web.backup.reader;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.core.role.Role;

public class RoleCSVParser extends CommonParser<Role> {

  public RoleCSVParser(DateTimeFormatter dateFormatter, DataManipulator<Role> dataManipulator, String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected Role parseEntity(CSVRecord record) {
    Role roleParsed = new Role();

    List<Field> fieldsToParse = getFields(roleParsed.getClass());

    parseObject(record, roleParsed, fieldsToParse);

    return roleParsed;
  }

  @Override
  public String getParserName() {
    return "roles";
  }
}
