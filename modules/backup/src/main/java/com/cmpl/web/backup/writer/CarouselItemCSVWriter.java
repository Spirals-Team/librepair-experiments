package com.cmpl.web.backup.writer;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.carousel.CarouselItem;

public class CarouselItemCSVWriter extends CommonWriter<CarouselItem> {

  public CarouselItemCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<CarouselItem> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "carousel_items";
  }

}
