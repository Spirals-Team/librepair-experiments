package com.cmpl.web.backup.reader;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.core.widget.WidgetPage;

public class WidgetPageCSVParser extends CommonParser<WidgetPage> {

  public WidgetPageCSVParser(DateTimeFormatter dateFormatter, DataManipulator<WidgetPage> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected WidgetPage parseEntity(CSVRecord record) {
    WidgetPage widgetPageParsed = new WidgetPage();

    List<Field> fieldsToParse = getFields(widgetPageParsed.getClass());

    parseObject(record, widgetPageParsed, fieldsToParse);

    return widgetPageParsed;
  }

  @Override
  public String getParserName() {
    return "widget_pages";
  }
}
