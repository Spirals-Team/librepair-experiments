package com.cmpl.web.backup.reader;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.core.media.Media;
import com.cmpl.web.core.media.MediaBuilder;



public class MediaCSVParser extends CommonParser<Media> {

  public MediaCSVParser(DateTimeFormatter dateFormatter, DataManipulator<Media> dataManipulator, String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected Media parseEntity(CSVRecord record) {
    Media mediaParsed = MediaBuilder.create().build();

    List<Field> fieldsToParse = getFields(mediaParsed.getClass());

    parseObject(record, mediaParsed, fieldsToParse);

    return mediaParsed;
  }

  @Override
  public String getParserName() {
    return "media";
  }

}
