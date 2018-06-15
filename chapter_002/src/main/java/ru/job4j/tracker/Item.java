package ru.job4j.tracker;

public class Item {
    private String id;
    private String name;
    private String description;
    private long created;
    private String comments;

    Item(String name, String description, long created) {
        this.name = name;
        this.description = description;
        this.created = created;
    }

    Item(String name, String desc) {
        this.name = name;
        this.description = desc;
    }

     Item() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Item" + "id='" + id + '\'' + ", name='" + name + '\'' + ", description='" + description + '\'' + ", created=" + created + ", comments='" + comments + '\'';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
