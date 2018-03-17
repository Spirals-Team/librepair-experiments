package ru.job4j.sql.items;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Объявление.
 * @author Hincu Andrei (andreih1981@gmail.com)on 25.12.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class Advert {
    private static final Logger LOG = LogManager.getLogger(Advert.class);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yy, HH:mm");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("d MMM yy, HH:mm");
    private static final SimpleDateFormat DATE_PREPARE = new SimpleDateFormat("d MMM yy");
    private int id;
    private String url;
    private String title;
    private String text;
    private Author author;
    private Calendar date;
    private Calendar publicationDate;

    public Advert() {
    }
    public Calendar getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(long data) {
        this.publicationDate = Calendar.getInstance();
        this.publicationDate.setTimeInMillis(data);
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(long data) {
        this.date = Calendar.getInstance();
        this.date.setTimeInMillis(data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Advert advert = (Advert) o;

        if (id != advert.id) {
            return false;
        }
        if (url != null ? !url.equals(advert.url) : advert.url != null) {
            return false;
        }
        if (title != null ? !title.equals(advert.title) : advert.title != null) {
            return false;
        }
        if (text != null ? !text.equals(advert.text) : advert.text != null) {
            return false;
        }
        if (author != null ? !author.equals(advert.author) : advert.author != null) {
            return false;
        }
        if (date != null ? !date.equals(advert.date) : advert.date != null) {
            return false;
        }
        return publicationDate != null ? publicationDate.equals(advert.publicationDate) : advert.publicationDate == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (publicationDate != null ? publicationDate.hashCode() : 0);
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String data = date == null ? "No time to show." : dateFormat.format(date.getTime());
        String publishData = publicationDate == null ? "No time to show." : dateFormat.format(publicationDate.getTime());
        return "Advert :"
                + "ID :"
                + id
                + System.lineSeparator()
                + "Title = '"
                + title
                + "\',"
                + System.lineSeparator()
                + "url = '" + url
                + '\''
                + System.lineSeparator()
                + "text = '"
                + text
                + '\''
                + System.lineSeparator()
                + "Author: "
                + author
                + System.lineSeparator()
                + "Date = "
                + data
                + System.lineSeparator()
                + "Publication date = "
                + publishData
                + System.lineSeparator()
                + "====================================================";
    }

    /**
     * Метод обрабатывает строку получая из нее дату.
     * @param data строка.
     * @return дата с использованием класса Calendar.
     */
    public static Calendar prepareDate(String data) {
        Calendar calendar = Calendar.getInstance();
        if (data != null) {
            final String today = "сегодня";
            final String yesterday = "вчера";
            data = data.replaceAll(today, DATE_PREPARE.format(calendar.getTime()));
            calendar.add(Calendar.DATE, -1);
            data = data.replaceAll(yesterday, DATE_PREPARE.format(calendar.getTime()));
            try {
                calendar.setTime(DATE_FORMAT.parse(data));
            } catch (ParseException e) {
                LOG.error("Can not process the date", e);
            }
        }
        return calendar;
    }
}
