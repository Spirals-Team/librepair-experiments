package codeu.model.util;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Util {

  /** Returns the date and time in the format: Thu, 14 Jun 2018 05:01:09 GMT */
  public static String FormatDateTime(Instant creationTime) {
    return DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneOffset.UTC).format(creationTime);
  }
}
