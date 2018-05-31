package com.cmpl.web.backup.writer;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.news.NewsContent;

public class NewsContentCSVWriter extends CommonWriter<NewsContent> {

  public NewsContentCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<NewsContent> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "news_contents";
  }

}
