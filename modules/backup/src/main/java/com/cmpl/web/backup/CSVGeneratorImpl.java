package com.cmpl.web.backup;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmpl.web.backup.writer.CSVGenerator;
import com.cmpl.web.backup.writer.CommonWriter;

public class CSVGeneratorImpl implements CSVGenerator {

  private static final Logger LOGGER = LoggerFactory.getLogger(CSVGeneratorImpl.class);

  private final List<CommonWriter<?>> writers;

  public CSVGeneratorImpl(List<CommonWriter<?>> writers) {
    this.writers = writers;
  }

  @Override
  public void extractAllDataToCSVFiles() {
    this.writers.stream().forEach(writer -> executeWriter(writer));
  }

  private void executeWriter(CommonWriter<?> writer) {
    LOGGER.info("Extraction des " + writer.getWriterName());
    writer.writeCSVFile();

  }

}
