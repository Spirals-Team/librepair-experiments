package com.cmpl.web.backup.writer;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.media.Media;

public class MediaCSVWriter extends CommonWriter<Media> {

  public MediaCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<Media> dataManipulator, String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "media";
  }

}
