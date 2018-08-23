package ru.job4j.jdbc.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Class Entry.
 */
@XmlRootElement(name = "entries")
public class Entry {
    /**
     * @param values list for saving data from db.
     */
    private List<Field> values;

    /**
     * Constructor №1.
     */
    public Entry() {
    }

    /**
     * Constructor №2.
     * @param values values
     */
    public Entry(List<Field> values) {
        this.values = values;
    }

    /**
     * Getter for values.
     * @return values
     */
    public List<Field> getValues() {
        return values;
    }

    /**
     * Setter for values.
     * @param values values
     */
    @XmlElement(name = "entry")
    public void setValues(List<Field> values) {
        this.values = values;
    }
}

