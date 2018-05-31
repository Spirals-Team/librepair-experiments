package com.cmpl.web.backup.reader;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.core.user.User;

public class UserCSVParser extends CommonParser<User> {

  public UserCSVParser(DateTimeFormatter dateFormatter, DataManipulator<User> dataManipulator, String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected User parseEntity(CSVRecord record) {
    User userParsed = new User();

    List<Field> fieldsToParse = getFields(userParsed.getClass());

    parseObject(record, userParsed, fieldsToParse);

    return userParsed;
  }

  @Override
  public String getParserName() {
    return "users";
  }
}
