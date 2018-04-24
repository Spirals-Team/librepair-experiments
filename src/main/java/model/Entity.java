package model;

import java.io.Serializable;

/**
 * Created by mariano on 23/04/18.
 */
public abstract class Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }
}
