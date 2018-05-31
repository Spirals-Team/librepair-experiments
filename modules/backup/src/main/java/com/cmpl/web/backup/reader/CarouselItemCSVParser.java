package com.cmpl.web.backup.reader;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.core.carousel.CarouselItem;

public class CarouselItemCSVParser extends CommonParser<CarouselItem> {

  public CarouselItemCSVParser(DateTimeFormatter dateFormatter, DataManipulator<CarouselItem> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  protected CarouselItem parseEntity(CSVRecord record) {
    CarouselItem carouselItemParsed = new CarouselItem();

    List<Field> fieldsToParse = getFields(carouselItemParsed.getClass());

    parseObject(record, carouselItemParsed, fieldsToParse);

    return carouselItemParsed;
  }

  @Override
  public String getParserName() {
    return "carousel_items";
  }

}
