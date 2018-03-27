package ru.job4j.model;

import java.sql.Timestamp;

/**
 * .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 08.03.18;
 * @version $Id$
 * @since 0.1
 */
public class Item {
    private int id;
    private String description;
    private Timestamp created;

    public Item() {
    }

    public int getId() {

        return id;
    }

    public Item(String description, Timestamp created, boolean done) {
        this.description = description;
        this.created = created;
        this.done = done;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    private boolean done;

    @Override
    public String toString() {
        return "Item{"
                + "id="
                + id
                + ", description='"
                + description
                + '\''
                + ", created="
                + created
                + ", done="
                + done
                + '}';
    }
}
