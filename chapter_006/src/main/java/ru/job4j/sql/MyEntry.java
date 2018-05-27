package ru.job4j.sql;

import javax.xml.bind.annotation.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class MyEntry {
    @XmlElement(name = "field")
    private int field;

    public void setField(int field) {
        this.field = field;
    }
}
