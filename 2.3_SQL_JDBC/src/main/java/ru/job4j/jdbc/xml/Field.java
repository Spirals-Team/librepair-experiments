package ru.job4j.jdbc.xml;

import javax.xml.bind.annotation.XmlElement;

/**
 * Class Field.
 */
public class Field {
    /**
     * @param value
     */
    private int value;

    /**
     * Constructor №1.
     */
    public Field() {
    }

    /**
     * Constructor №2.
     * @param value value
     */
    public Field(int value) {
        this.value = value;
    }

    /**
     * Getter for value.
     * @return values
     */
    public int getValue() {
        return value;
    }

    /**
     * Setter for value.
     * @param value value for setting
     */
    @XmlElement(name = "field")
    public void setValue(int value) {
        this.value = value;
    }
}
