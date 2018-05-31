package com.cmpl.web.backup.reader;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.core.role.Privilege;

public class PrivilegeCSVParser extends CommonParser<Privilege> {

  public PrivilegeCSVParser(DateTimeFormatter dateFormatter, DataManipulator<Privilege> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected Privilege parseEntity(CSVRecord record) {
    Privilege privilegeParsed = new Privilege();

    List<Field> fieldsToParse = getFields(privilegeParsed.getClass());

    parseObject(record, privilegeParsed, fieldsToParse);

    return privilegeParsed;
  }

  @Override
  public String getParserName() {
    return "privileges";
  }
}
