package by.imsha.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author Alena Misan
 */
public class ServiceUtils {

    private static String dateFormat = "dd-MM-yyyy";

    public static String[] parseSortValue(String sort){
        if(StringUtils.isBlank(sort)){
            return null;
        }

        String[] values = new String[2];
        if(sort.length() > 1 && (sort.startsWith("+") || sort.startsWith("-"))){
            values[0]=sort.substring(1);
            values[1]=String.valueOf(sort.charAt(0));
        }else{
            values[0] = sort;
            values[1] = "+";
        }
        return values;

    }


    public static LocalDateTime timestampToLocalDate(long timestamp, ZoneId zoneId){
        return ZonedDateTime.ofInstant ( Instant.ofEpochSecond ( timestamp ) , zoneId ).toLocalDateTime();
    }

    public static long dateToUTCTimestamp(String day) throws DateTimeParseException{
        LocalDate date = null;
        if(day != null){
            try{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
                date = LocalDate.parse(day, formatter);
            }catch (DateTimeParseException ex){
                throw new DateTimeParseException(String.format("Date format is incorrect. Date - %s,format - %s ", day, dateFormat), ex.getParsedString(), ex.getErrorIndex());
            }
        }

        return date.atStartOfDay().toEpochSecond(ZoneOffset.UTC);
    }
  
    public static ZonedDateTime localDateTimeToZoneDateTime(LocalDateTime localDateTime, ZoneId fromZone, ZoneId toZone) {
        ZonedDateTime date = ZonedDateTime.of(localDateTime, fromZone);
        return date.withZoneSameInstant(toZone);
    }

}
