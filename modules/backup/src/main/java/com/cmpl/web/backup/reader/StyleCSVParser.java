package com.cmpl.web.backup.reader;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.core.style.Style;

public class StyleCSVParser extends CommonParser<Style> {

  public StyleCSVParser(DateTimeFormatter dateFormatter, DataManipulator<Style> dataManipulator, String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected Style parseEntity(CSVRecord record) {
    Style styleParsed = new Style();

    List<Field> fieldsToParse = getFields(styleParsed.getClass());

    parseObject(record, styleParsed, fieldsToParse);

    return styleParsed;
  }

  @Override
  public String getParserName() {
    return "styles";
  }

}
