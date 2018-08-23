package ru.job4j.sqlru;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class Time.
 */
public class Time {
    /**
     * @param LOGGER logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Time.class);
    /**
     * @param finish date for finishing parsing
     */
    private LocalDateTime finish;
    /**
     * @param toDay today
     */
    private final String toDay = "сегодня";
    /**
     * @param yesterday yesterday
     */
    private final String yesterday = "вчера";
    /**
     * @param wrongMay wrongMay
     */
    private final String wrongMay = "май";
    /**
     * @param rightMay rightMay
     */
    private final String rightMay = "мая";

    /**
     * Method for equals date.
     * @param date date
     * @return result
     */
    public boolean equalsDate(LocalDateTime date) {
        return this.finish.isBefore(date);
    }

    /**
     * Method for convert String to LocalDateTime.
     * @param date string for converting
     * @return LocalDateTime
     */
    public LocalDateTime convertString(String date) {
        date = useRightDate(date);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d MMM yy, HH:mm");
        return  LocalDateTime.parse(date, dtf);
    }

    /**
     * Method for convert LocalDateTime to String.
     * @param date LocalDateTime for converting
     * @return String
     */
    public String convertDate(LocalDateTime date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d MMM yy, HH:mm");
        return date.format(dtf).toString();
    }

    /**
     * Method for error correction in date.
     * @param s date
     * @return correct date
     */
    public String useRightDate(String s) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d MMM yy");
        if (s.contains(toDay)) {
            s =  s.replace(toDay, LocalDate.now().format(dtf));
        } else if (s.contains(yesterday)) {
            s = s.replace(yesterday, LocalDate.now().minusDays(1).format(dtf));
        } else if (s.contains(wrongMay)) {
            s = s.replace(wrongMay, rightMay);
        }
        return s;
    }

    /**
     * Setter for finish.
     * @param finish value for finish
     */
    public void setFinish(LocalDateTime finish) {
        this.finish = finish;
    }
}
