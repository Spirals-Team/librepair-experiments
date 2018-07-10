package ru.job4j.interview;

import java.sql.Timestamp;

/**
 * Class represent Vacancy.
 */
public class Vacancy {

    private int id;
    private String title;
    private String link;
    private String description;
    private Timestamp lastUpdate;

    public boolean isEmpty() {
        return this.id == 0;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(final String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(final Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean checkTitle(final String matcher) {
        return this.title.toLowerCase().contains(matcher);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vacancy)) {
            return false;
        }
        Vacancy vacancy = (Vacancy) o;
        return id == vacancy.id
            && (title != null ? title.equals(vacancy.title) : vacancy.title == null)
            && (link != null ? link.equals(vacancy.link) : vacancy.link == null)
            && (description != null
            ? description.equals(vacancy.description) : vacancy.description == null)
            && (lastUpdate != null
            ? lastUpdate.equals(vacancy.lastUpdate) : vacancy.lastUpdate == null);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("Vacancy{id=%d, title='%s', link='%s', description='%s',"
            + " lastUpdate=%s}", id, title, link, description, lastUpdate);
    }
}

