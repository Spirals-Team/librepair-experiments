package com.cmpl.web.backup.reader;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.core.common.dao.BaseEntity;
import com.cmpl.web.core.common.reflexion.CommonReflexion;

public abstract class CommonParser<T extends BaseEntity> extends CommonReflexion {

  private static final Logger LOGGER = LoggerFactory.getLogger(CommonParser.class);

  private final DateTimeFormatter dateFormatter;
  private final DataManipulator<T> dataManipulator;
  private final String backupFilePath;

  public CommonParser(DateTimeFormatter dateFormatter, DataManipulator<T> dataManipulator, String backupFilePath) {
    this.dateFormatter = dateFormatter;
    this.dataManipulator = dataManipulator;
    this.backupFilePath = backupFilePath;
  }

  public void parseCSVFile() {
    List<T> parsedEntities = new ArrayList<>();
    try {
      Reader in = new FileReader(backupFilePath + File.separator + getCSVFileName());
      Iterable<CSVRecord> records = buildCSVFormat().parse(in);
      for (CSVRecord record : records) {
        parsedEntities.add(parseEntity(record));
      }
      saveEntities(parsedEntities);
    } catch (Exception e) {
      LOGGER.error("Impossible de lire le fichier " + getCSVFileName(), e);
    }
  }

  protected void saveEntities(List<T> entities) {
    dataManipulator.insertData(entities);
  }

  protected void parseObject(CSVRecord record, T objectToFill, List<Field> fieldsToParse) {
    for (Field field : fieldsToParse) {
      if (!field.isAccessible()) {
        field.setAccessible(true);
      }
      try {
        field.set(objectToFill, parseValueFromRecord(record, field));
      } catch (Exception e) {
        LOGGER.error("Impossible de parser le field " + field.getName(), e);
      }
    }
  }

  protected Object parseValueFromRecord(CSVRecord record, Field field) {

    String fieldName = field.getName();

    if (Long.class.equals(field.getType()) || long.class.equals(field.getType())) {
      return parseLong(record, fieldName);
    }

    if (Date.class.equals(field.getType())) {
      return parseDate(record.get(fieldName));
    }

    if (String.class.equals(field.getType())) {
      return parseString(record, fieldName);
    }

    if (Boolean.class.equals(field.getType()) || boolean.class.equals(field.getType())) {
      return parseBoolean(record, fieldName);
    }

    if (LocalDate.class.equals(field.getType())) {
      return parseLocalDate(record, fieldName);
    }

    if (LocalDateTime.class.equals(field.getType())) {
      return parseLocalDateTime(record, fieldName);
    }

    if (Integer.class.equals(field.getType()) || int.class.equals(field.getType())) {
      return parseInteger(record, fieldName);
    }

    if (Locale.class.equals(field.getType())) {
      return parseLocale(record, fieldName);
    }

    if (byte[].class.equals(field.getType())) {
      return parseByteArray(record, fieldName);
    }

    if (BigDecimal.class.equals(field.getType())) {
      return parseBigDecimal(record, fieldName);
    }

    if (List.class.isAssignableFrom(field.getType())) {
      return parseListString(record, fieldName);
    }

    return null;

  }

  private Long parseLong(CSVRecord record, String fieldName) {
    String longValue = record.get(fieldName);
    if (!StringUtils.hasText(longValue)) {
      return null;
    }
    return Long.valueOf(longValue);
  }

  private Date parseDate(String dateToParse) {
    if (!StringUtils.hasText(dateToParse)) {
      return null;
    }
    if ("null".equals(dateToParse)) {
      return null;
    }
    String checkedDateToParse = dateToParse;
    if (checkedDateToParse.length() > 19) {
      checkedDateToParse = checkedDateToParse.substring(0, 19);
    }
    return Date.from(LocalDateTime.from(dateFormatter.parse(checkedDateToParse)).atZone(ZoneId.systemDefault())
        .toInstant());
  }

  private String parseString(CSVRecord record, String propertyName) {
    return record.get(propertyName);
  }

  private boolean parseBoolean(CSVRecord record, String propertyName) {
    String booleanToParse = record.get(propertyName);
    if (!StringUtils.hasText(booleanToParse)) {
      return false;
    }
    return Boolean.valueOf(booleanToParse);
  }

  private int parseInteger(CSVRecord record, String fieldName) {
    String intValue = record.get(fieldName);
    if (!StringUtils.hasText(intValue)) {
      return 0;
    }
    return Integer.valueOf(intValue);
  }

  private LocalDate parseLocalDate(CSVRecord record, String fieldName) {
    String localDate = record.get(fieldName);
    if (!StringUtils.hasText(localDate)) {
      return null;
    }
    if ("null".equals(localDate)) {
      return null;
    }
    String checkedDateToParse = localDate;
    if (checkedDateToParse.length() > 19) {
      checkedDateToParse = checkedDateToParse.substring(0, 19);
    }
    return LocalDate.from(dateFormatter.parse(checkedDateToParse));

  }

  private LocalDateTime parseLocalDateTime(CSVRecord record, String fieldName) {
    String localDate = record.get(fieldName);
    if (!StringUtils.hasText(localDate)) {
      return null;
    }
    if ("null".equals(localDate)) {
      return null;
    }
    String checkedDateToParse = localDate;
    if (checkedDateToParse.length() > 19) {
      checkedDateToParse = checkedDateToParse.substring(0, 19);
    }
    return LocalDateTime.from(dateFormatter.parse(checkedDateToParse));

  }

  private byte[] parseByteArray(CSVRecord record, String fieldName) {
    String byteArrayToParse = record.get(fieldName);
    if (!StringUtils.hasText(byteArrayToParse)) {
      return new byte[]{};
    }
    return byteArrayToParse.getBytes();
  }

  private Locale parseLocale(CSVRecord record, String fieldName) {
    String localeToParse = record.get(fieldName);
    if (!StringUtils.hasText(localeToParse)) {
      return Locale.FRANCE;
    }
    return Locale.forLanguageTag(localeToParse);
  }

  private BigDecimal parseBigDecimal(CSVRecord record, String fieldName) {
    String bigDecimalToParse = record.get(fieldName);
    if (!StringUtils.hasText(bigDecimalToParse)) {
      return BigDecimal.ZERO;
    }

    return new BigDecimal(bigDecimalToParse);

  }

  private List<String> parseListString(CSVRecord record, String fieldName) {
    String stringToParse = record.get(fieldName);
    if (!StringUtils.hasText(stringToParse)) {
      return new ArrayList<>();
    }
    return Arrays.asList(stringToParse.split(";"));
  }

  protected CSVFormat buildCSVFormat() {
    return CSVFormat.EXCEL.withHeader().withQuoteMode(QuoteMode.ALL);
  }

  protected abstract T parseEntity(CSVRecord record);

  public abstract String getParserName();

  protected String getCSVFileName() {
    return getParserName() + ".csv";
  }

}
