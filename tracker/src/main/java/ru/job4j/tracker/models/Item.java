package ru.job4j.tracker.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Item - элемент трекера.
 * @author Hincu Andrei (andreih1981@gmail.com)on 04.09.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class Item {
    /**
     * id.
     */
   private String id;
    /**
     * name.
     */
    private String name;

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**

     * desc.
     */
   private String desc;
    /**
     * created.
     */
    private long created;
    /**
     * comment.
     */
    private List<Comment> comments;

    public Item(String id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    /**
     * Конструктор класса.
     * @param name имя заякки.
     * @param desc описание.
     */
    public Item(String name, String desc) {
        this.name = name;
        this.desc = desc;
        this.created = System.currentTimeMillis();
    }

    /**
     * Метод добовляет новый коментарий к заявке.
     * @param comment коментарий.
     */
    public void addComment(String comment) {
        if (this.comments == null) {
            this.comments = new ArrayList<>();
        }
        this.comments.add(new Comment(comment));
    }

    public long getCreated() {
        return created;
    }

    /**
     * Конструктор.
     */
    public Item() {

    }

    public Item(String name, String desc, long created) {
        this.name = name;
        this.desc = desc;
        this.created = created;
    }

    /**
     * геттер id.
     * @return id.
     */
    public String getId() {
        return id;
    }

    /**
     * сеттер id.
     * @param id id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * геттер name.
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * сеттер name.
     * @param name name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * геттер desk.
     * @return desk.
     */
    public String getDesc() {
        return desc;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    /**
     * сеттер деск.
     * @param desc desk
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

//    @Override
//    public String toString() {
//        return "Item{" +
//                "id='" + id + '\'' +
//                ", name='" + name + '\'' +
//                ", desc='" + desc + '\'' +
//                ", created=" + created +
//                ", comments=" + comments +
//                '}';
//    }

    @Override
    public String toString() {
        return "Item{"
                + "id = '"
                + id
                + '\''
                + ", name = '"
                + name
                + '\''
                + ", desc = '"
                + desc
                + '\''
                + ", created : "
                + new SimpleDateFormat("dd-MM-YYYY").format(new Date(created))
                + '}';
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

//        if (created != item.created) {
//            return false;
//        }
        if (id != null ? !id.equals(item.id) : item.id != null) {
            return false;
        }
        if (name != null ? !name.equals(item.name) : item.name != null) {
            return false;
        }
        if (desc != null ? !desc.equals(item.desc) : item.desc != null) {
            return false;
        }
        return comments != null ? comments.equals(item.comments) : item.comments == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (int) (created ^ (created >>> 32));
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        return result;
    }
}
