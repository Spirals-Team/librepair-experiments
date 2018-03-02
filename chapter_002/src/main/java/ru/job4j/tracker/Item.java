package ru.job4j.tracker;
/**
 * Item.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class Item {
    /**
     * id of item.
     */
    private String id;
    /**
     * name of item.
     */
    private String name;
    /**
     * description of item.
     */
    private String desc;
    /**
     * Time of creation.
     */
    private long created;
    /**
     * Comments.
     */
    private String[] comments;
    /**
     * Constructor with parameters.
     * @param name - name
     * @param desc - description
     * @param created - time of creation
     */
    Item(String name, String desc, long created) {
        this.name = name;
        this.desc = desc;
        this.created = created;
    }
    /**
     * Get id.
     * @return id
     */
    public String getId() {
        return this.id;
    }
    /**
     * Set id.
     * @param id - id
     */
    public  void setId(String id) {
        this.id = id;
    }
    /**
     * Get name.
     * @return name
     */
    public String getName() {
        return this.name;
    }
    /**
     * Get description.
     * @return description
     */
    public String getDescription() {
        return this.desc;
    }
    /**
     * Get time of creation.
     * @return created
     */
    public long getCreated() {
        return this.created;
    }
    /**
     * Get comments.
     * @return comments
     */
    public String[] getComments() {
        return this.comments;
    }
}
