package ru.job4j.model.car;

import ru.job4j.model.Persistent;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 20.03.2018.
 * @version $Id$.
 * @since 0.1.
 */

@Entity @Table(name = "transmission")
public class Transmission extends Persistent {

    private static final long serialVersionUID = 5784205413731240979L;
    private String type;

    public Transmission() {
        super();
    }

    public Transmission(Long id) {
        super(id);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
