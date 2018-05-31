package com.cmpl.web.backup.reader;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.core.news.NewsImage;

public class NewsImageCSVParser extends CommonParser<NewsImage> {

  public NewsImageCSVParser(DateTimeFormatter dateFormatter, DataManipulator<NewsImage> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected NewsImage parseEntity(CSVRecord record) {
    NewsImage newsImageParsed = new NewsImage();

    List<Field> fieldsToParse = getFields(newsImageParsed.getClass());

    parseObject(record, newsImageParsed, fieldsToParse);

    return newsImageParsed;
  }

  @Override
  public String getParserName() {
    return "news_images";
  }

}
