package ru.job4j.sqlru.items;

import java.util.Date;

public class Vacancy {
    private String title;
    private String url;
    private String text;
    private String author;
    private Date date;

    public Vacancy(String title, String url, String text, String author, Date date) {
        this.title = title;
        this.url = url;
        this.text = text;
        this.author = author;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
