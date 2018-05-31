package com.cmpl.web.backup.writer;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.news.NewsEntry;

public class NewsEntryCSVWriter extends CommonWriter<NewsEntry> {

  public NewsEntryCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<NewsEntry> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "news_entries";
  }

}
