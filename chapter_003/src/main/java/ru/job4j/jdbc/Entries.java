package ru.job4j.jdbc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

/**
 * Class represent set of entries.
 */
@XmlRootElement
public class Entries {

    private Set<Entry> entry;

    public Entries() {
    }

    public Entries(Set<Entry> entry) {
        this.entry = entry;
    }

    /**
     * Get entry
     *
     * @return entry set
     */
    public Set<Entry> getEntry() {
        return entry;
    }

    /**
     * Set entry
     *
     * @param entry entry
     */
    @XmlElement
    public void setEntry(Set<Entry> entry) {
        this.entry = entry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entries entries1 = (Entries) o;
        return entry != null
            ? entry.equals(entries1.entry) : entries1.entry == null;
    }

    @Override
    public int hashCode() {
        int result = 17;
        return 31 * result + entry.hashCode();
    }

    @Override
    public String toString() {
        return String.format("Entries{entry=%s}", entry);
    }
}
