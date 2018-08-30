package ru.job4j.jdbc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class represent Entry entity.
 */
@XmlRootElement
public class Entry {

    private int field;

    public Entry() {
    }

    public Entry(int field) {
        this.field = field;
    }

    /**
     * Get field value
     *
     * @return field
     */
    public int getField() {
        return field;
    }

    /**
     * Set field value
     *
     * @param field field
     */
    @XmlElement
    public void setField(int field) {
        this.field = field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entry entry1 = (Entry) o;
        return field == entry1.field;
    }

    @Override
    public int hashCode() {
        return field;
    }

    @Override
    public String toString() {
        return String.format("Entry{field=%d}", field);
    }
}
