package com.cmpl.web.backup.reader;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.core.menu.Menu;

public class MenuCSVParser extends CommonParser<Menu> {

  public MenuCSVParser(DateTimeFormatter dateFormatter, DataManipulator<Menu> dataManipulator, String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected Menu parseEntity(CSVRecord record) {
    Menu menuParsed = new Menu();

    List<Field> fieldsToParse = getFields(menuParsed.getClass());

    parseObject(record, menuParsed, fieldsToParse);

    return menuParsed;
  }

  @Override
  public String getParserName() {
    return "menu";
  }

}
