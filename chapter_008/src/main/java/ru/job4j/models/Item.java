package ru.job4j.models;

import java.sql.Timestamp;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Item {
    private int id;
    private String desc;
    private Timestamp created;
    private boolean done;

    public Item() {
    }

    public Item(String desc) {
        this.desc = desc;
        this.created = new Timestamp(System.currentTimeMillis());
        this.done = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return String.format("{\"id\":\"%s\", \"desc\":\"%s\", \"time\":\"%s\", \"done\":\"%s\"}",
                this.id, this.desc, this.created, this.done);
    }
}
