package com.cmpl.web.backup.reader;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.core.news.NewsContent;

public class NewsContentCSVParser extends CommonParser<NewsContent> {

  public NewsContentCSVParser(DateTimeFormatter dateFormatter, DataManipulator<NewsContent> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected NewsContent parseEntity(CSVRecord record) {
    NewsContent newsContentParsed = new NewsContent();

    List<Field> fieldsToParse = getFields(newsContentParsed.getClass());

    parseObject(record, newsContentParsed, fieldsToParse);

    return newsContentParsed;
  }

  @Override
  public String getParserName() {
    return "news_contents";
  }

}
