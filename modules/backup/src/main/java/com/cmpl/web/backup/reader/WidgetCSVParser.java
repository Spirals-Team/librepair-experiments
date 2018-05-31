package com.cmpl.web.backup.reader;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.core.widget.Widget;

public class WidgetCSVParser extends CommonParser<Widget> {

  public WidgetCSVParser(DateTimeFormatter dateFormatter, DataManipulator<Widget> dataManipulator, String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected Widget parseEntity(CSVRecord record) {
    Widget widgetParsed = new Widget();

    List<Field> fieldsToParse = getFields(widgetParsed.getClass());

    parseObject(record, widgetParsed, fieldsToParse);

    return widgetParsed;
  }

  @Override
  public String getParserName() {
    return "widgets";
  }
}
