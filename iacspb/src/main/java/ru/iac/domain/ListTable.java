package ru.iac.domain;

import ru.iac.utils.CompareIntInText;
import javax.persistence.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
@Entity(name = "lists")
public class ListTable implements Comparable<ListTable> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String size;

    @ManyToOne
    @JoinColumn(name = "id_path")
    private PathTable pathTable;

    public ListTable() {
    }

    public ListTable(String name, String size, PathTable pathTable) {
        this.name = name;
        this.size = size;
        this.pathTable = pathTable;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public PathTable getPathTable() {
        return pathTable;
    }

    public void setPathTable(PathTable pathTable) {
        this.pathTable = pathTable;
    }

    @Override
    public int compareTo(ListTable o) {
        if (this.getSize().equals("DIR")) {
            if (o.getSize().equals("DIR")) {
                return CompareIntInText.compareString(this.getName(), o.getName());
            } else {
                return -1;
            }
        } else if (o.getSize().equals("DIR")) {
            return 1;
        } else {
            return CompareIntInText.compareString(this.getName(), o.getName());
        }
    }
}
