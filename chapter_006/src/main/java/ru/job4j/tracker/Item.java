package ru.job4j.tracker;

import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class Item {

    private String id;
    private String name;
    private String desc;
    private String created;
    private List<String> comments;

    public Item(String name, String desc) {
        this.name = name;
        this.desc = desc;
    };

    public Item(String id, String name, String desc, String created, List<String> comments) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.created = created;
        this.comments = comments;
    }

    public String getName() {
        return this.name;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    };

    public String getCreated() {
        return this.created;
    }

    public List<String> getComments() {
        return this.comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public void addComment(String st) {
        this.comments.add(st);
    }
}
