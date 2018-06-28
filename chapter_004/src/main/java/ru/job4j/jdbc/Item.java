package ru.job4j.jdbc;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Item {

    private String id;
    private String name;
    private String desc;
    private long created;
    private String comments;

    public Item() {
    }

    public Item(String name, String description, long created, String comments) {
        this.name = name;
        this.desc = description;
        this.created = created;
        this.comments = comments;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return this.comments;
    }

    public void setCreated(long id) {
        this.created = created;
    }

    public long getCreated() {
        return this.created;
    }
}
