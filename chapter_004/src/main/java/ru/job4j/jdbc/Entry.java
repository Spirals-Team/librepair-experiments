package ru.job4j.jdbc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class Entry {
    public Entry() {
    }

    public Entry(int field) {
        this.field = field;
    }

    @XmlElement
    private int field;

    public int getField() {
        return field;
    }

    public void setField(int field) {
        this.field = field;
    }
}
