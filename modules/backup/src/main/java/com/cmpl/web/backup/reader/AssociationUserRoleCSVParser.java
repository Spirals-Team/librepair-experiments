package com.cmpl.web.backup.reader;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.core.models.Responsibility;

public class AssociationUserRoleCSVParser extends CommonParser<Responsibility> {

  public AssociationUserRoleCSVParser(DateTimeFormatter dateFormatter, DataManipulator<Responsibility> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected Responsibility parseEntity(CSVRecord record) {
    Responsibility associationParsed = new Responsibility();

    List<Field> fieldsToParse = getFields(associationParsed.getClass());

    parseObject(record, associationParsed, fieldsToParse);

    return associationParsed;
  }

  @Override
  public String getParserName() {
    return "associations_user_role";
  }
}
