package ru.job4j.jdbc.xmlexemples;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 15.12.2017.
 * @version $Id$.
 * @since 0.1.
 */
@XmlRootElement(name = "entries")
public class Entries {
    public Entries(List<Field> fields) {
        this.fields = fields;
    }
    public Entries getEntries(int count) {
        Entries e = new Entries();
        return e;
    }
    private List<Field> fields;

    public Entries() {
    }
    @XmlElement(name = "entry")
    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    class Field {
        private int field;
        @XmlElement(name = "field")
        public int getField() {
            return field;
        }

        public Field() {
        }

        public Field(int field) {
            this.field = field;
        }
    }
}
