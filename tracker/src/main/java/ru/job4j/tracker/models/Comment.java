package ru.job4j.tracker.models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 10.12.17;
 * @version $Id$
 * @since 0.1
 */
public class Comment {
    private String text;
    private long create;
    private int id;

    public Comment(String text) {
        this.text = text;
        this.create = System.currentTimeMillis();
    }

    public Comment() {
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreate(long create) {
        this.create = create;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Comment{"
                + "Id = "
                + id
                + ", text = '"
                + text
                + '\''
                + ", create = "
                + new SimpleDateFormat("dd-MM-YYYY").format(new Date(create))
                + '}';
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
