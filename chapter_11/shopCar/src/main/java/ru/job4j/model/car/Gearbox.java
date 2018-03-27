package ru.job4j.model.car;

import ru.job4j.model.Persistent;

import javax.persistence.*;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 20.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
@Entity @Table(name = "gearbox")
public class Gearbox extends Persistent {

    private static final long serialVersionUID = -9130492815669266694L;
    private String type;

    public Gearbox() {
        super();
    }

    public Gearbox(Long id) {
       super(id);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
