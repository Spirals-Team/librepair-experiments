package ru.job4j.tracker;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class Item {
    private String id = this.toString();
    private String name;
    private String description;
    private long create;
    private String[] comments;

    public Item(String name, String description, long create) {
        this.name = name;
        this.description = description;
        this.create = create;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreate(long create) {
        this.create = create;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public long getCreate() {
        return create;
    }
}
