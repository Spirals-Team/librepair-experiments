package ru.job4j.hibernate.models;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Class Item.
 */
public class Item {

    private Long id;
    private String description;
    private Timestamp created;
    private boolean done;

    public Item() {
    }

    public Item(final String description, final boolean done) {
        this.description = description;
        this.done = done;
    }

    public Item(final Long id, final String description, final Timestamp created,
                final boolean done) {
        this.id = id;
        this.description = description;
        this.created = created;
        this.done = done;
    }

    public Long getId() {
        return id;
    }

    public Item setId(final Long id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Item setDescription(final String description) {
        this.description = description;
        return this;
    }

    public Timestamp getCreated() {
        return created;
    }

    public Item setCreated(final Timestamp created) {
        this.created = created;
        return this;
    }

    public boolean isDone() {
        return done;
    }

    public Item setDone(final boolean done) {
        this.done = done;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item that = (Item) o;
        return Objects.equals(this.id, that.id)
            && Objects.equals(this.description, that.description)
            && Objects.equals(this.created, that.created)
            && Objects.equals(this.done, that.done);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, created, done);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
            .add("id = " + id)
            .add("description = " + description)
            .add("created = " + created)
            .add("done = " + done)
            .toString();
    }
}
