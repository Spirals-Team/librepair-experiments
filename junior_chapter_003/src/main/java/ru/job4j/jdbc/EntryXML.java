package ru.job4j.jdbc;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 07.03.2018.
 * @version 1.0.
 * @since 0.1.
 */
public class EntryXML {

    private int id;

    public EntryXML(int id) {
        this.id = id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @XmlElement(name = "field")
    public int getId() {
        return id;
    }

}

@XmlRootElement(name = "entries")
class Entries {

    @XmlElement (name = "entry")
    private List<EntryXML> entries = new ArrayList<>();

    public void setEntries(List<EntryXML> entriesXML) {
        this.entries = entriesXML;
    }
}
