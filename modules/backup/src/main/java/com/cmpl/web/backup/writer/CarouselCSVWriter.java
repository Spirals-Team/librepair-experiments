package com.cmpl.web.backup.writer;

import java.time.format.DateTimeFormatter;

import com.cmpl.web.core.carousel.Carousel;

public class CarouselCSVWriter extends CommonWriter<Carousel> {

  public CarouselCSVWriter(){

  }

  public CarouselCSVWriter(DateTimeFormatter dateFormatter, DataManipulator<Carousel> dataManipulator,
      String backupFilePath) {
    super(dateFormatter, dataManipulator, backupFilePath);
  }

  @Override
  public String getWriterName() {
    return "carousels";
  }

}
