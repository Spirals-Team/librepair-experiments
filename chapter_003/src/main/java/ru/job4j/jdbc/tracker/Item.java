package ru.job4j.jdbc.tracker;

/**
 * Tracker's Item.
 *
 * @version $Id$
 * @since 0.1
 */
public class Item {

    private int id;
    private String name;
    private String description;
    private long createdDate;

    public Item() {
    }

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return id == item.id
            && createdDate == item.createdDate
            && (name != null ? name.equals(item.name) : item.name == null)
            && (description != null
            ? description.equals(item.description) : item.description == null);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (int) (createdDate ^ (createdDate >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return String.format("Item{id=%d, name='%s', description='%s', createdDate=%d}",
            id, name, description, createdDate);
    }
}
