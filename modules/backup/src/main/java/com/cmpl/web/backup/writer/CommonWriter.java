package com.cmpl.web.backup.writer;

import java.io.FileWriter;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.cmpl.web.core.common.dao.BaseEntity;

public abstract class CommonWriter<T extends BaseEntity> extends BaseCSVWriter {

  private static final Logger LOGGER = LoggerFactory.getLogger(CommonWriter.class);

  private final DateTimeFormatter dateFormatter;
  private final DataManipulator<T> dataManipulator;
  private final String backupFilePath;

  public CommonWriter() {
    dateFormatter = null;
    dataManipulator = null;
    backupFilePath = null;
  }

  public CommonWriter(DateTimeFormatter dateFormatter, DataManipulator<T> dataManipulator, String backupFilePath) {
    this.dateFormatter = dateFormatter;
    this.dataManipulator = dataManipulator;
    this.backupFilePath = backupFilePath;
  }

  public void writeCSVFile() {
    List<T> entitiesToWrite = dataManipulator.extractData();
    if (!CollectionUtils.isEmpty(entitiesToWrite)) {
      try {

        FileWriter fileWriter = createFileWriterForEntity(backupFilePath, getCSVFileName());
        CSVPrinter csvFilePrinter = createCSVPrinter(fileWriter);

        List<String> fileHeader = getFileHeader(entitiesToWrite.get(0));
        csvFilePrinter.printRecord(fileHeader);

        for (T entity : entitiesToWrite) {
          csvFilePrinter.printRecord(parseEntityToListString(entity));
        }

        fileWriter.flush();
        fileWriter.close();
        csvFilePrinter.close();

      } catch (Exception e) {
        LOGGER.error("Erreur lors de la generation du fichier " + getCSVFileName(), e);
      }
    }

  }

  protected List<String> parseEntityToListString(T entityToWrite) {

    List<String> records = new ArrayList<>();
    getFields(entityToWrite.getClass()).forEach(field -> records.add(parseObjectValueToString(field, entityToWrite)));
    return records;

  }

  protected String parseObjectValueToString(Field field, T entityToWrite) {
    String result = "";
    if (!field.isAccessible()) {
      field.setAccessible(true);
    }

    try {
      if (List.class.isAssignableFrom(field.getType())) {
        return parseListString(field, entityToWrite);
      }

      if (Locale.class.equals(field.getType())) {
        return parseLocale(field, entityToWrite);
      }

      if (Date.class.equals(field.getType())) {
        return parseDate(field, entityToWrite);
      }

      if (LocalDate.class.equals(field.getType())) {
        return parseLocalDate(field, entityToWrite);
      }

      if (LocalDateTime.class.equals(field.getType())) {
        return parseLocalDateTime(field, entityToWrite);
      }

      if (byte[].class.equals(field.getType())) {
        return parseByteArray(field, entityToWrite);
      }

      return parseObject(field, entityToWrite);
    } catch (Exception e) {
      LOGGER.error(
          "Impossible de parser le field : " + field.getName() + " pour l'objet : " + entityToWrite.getClass(), e);
    }

    return result;
  }

  protected String parseDate(Field field, T entityToWrite) throws Exception {
    Date dateToParse = (Date) field.get(entityToWrite);
    return dateFormatter.format(dateToParse.toInstant());

  }

  protected String parseLocalDate(Field field, T entityToWrite) throws Exception {
    LocalDate localDateToParse = (LocalDate) field.get(entityToWrite);
    return dateFormatter.format(localDateToParse);

  }

  protected String parseLocalDateTime(Field field, T entityToWrite) throws Exception {
    LocalDateTime localDateToParse = (LocalDateTime) field.get(entityToWrite);
    return dateFormatter.format(localDateToParse);

  }

  protected String parseLocale(Field field, T entityToWrite) throws Exception {
    return ((Locale) field.get(entityToWrite)).getLanguage();
  }

  protected String parseListString(Field field, T entityToWrite) throws Exception {
    if (field.get(entityToWrite) == null) {
      return "";
    }
    return String.join(";", (List<String>) field.get(entityToWrite));
  }

  protected String parseObject(Field field, T entityToWrite) throws Exception {
    String result = String.valueOf(field.get(entityToWrite));
    if (!StringUtils.hasText(result) || "null".equals(result)) {
      result = "";
    }
    return result;
  }

  protected String parseByteArray(Field field, T entityToWrite) throws Exception {
    byte[] bytes = (byte[]) field.get(entityToWrite);
    if (bytes == null) {
      return "";
    }
    return new String(bytes, StandardCharsets.UTF_8);
  }

  protected List<String> getFileHeader(T entity) {

    List<Field> fieldsToParse = getFields(entity.getClass());
    List<String> fileHeader = new ArrayList<>();
    fieldsToParse.forEach(field -> {
      if (!field.isAccessible()) {
        field.setAccessible(true);
      }
      if (!field.isSynthetic()) {
        fileHeader.add(field.getName());
      }

    });
    return fileHeader;
  }

  public abstract String getWriterName();

  protected String getCSVFileName() {
    return getWriterName() + ".csv";
  }

}
