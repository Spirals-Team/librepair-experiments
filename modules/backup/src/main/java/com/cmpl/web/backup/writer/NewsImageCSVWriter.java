package com.cmpl.web.backup.writer;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.news.NewsImage;

public class NewsImageCSVWriter extends CommonWriter<NewsImage> {

  public NewsImageCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<NewsImage> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "news_images";
  }

}
