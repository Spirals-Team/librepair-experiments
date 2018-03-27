package ru.job4j.model.car;

import ru.job4j.model.Persistent;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 20.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
@Entity @Table(name = "carcass")
public class Carcass extends Persistent {

    private static final long serialVersionUID = 2288089886123459967L;
    private String type;

    public Carcass(Long id) {
        super(id);
    }

    public Carcass() {
    }

    public Carcass(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
