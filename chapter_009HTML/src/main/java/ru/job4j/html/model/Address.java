package ru.job4j.html.model;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 05.02.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class Address {
    private static final Logger LOG = LogManager.getLogger(Address.class);
    private String id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Address address = (Address) o;

        if (id != null ? !id.equals(address.id) : address.id != null) {
            return false;
        }
        return name != null ? name.equals(address.name) : address.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Address{"
                + "id='"
                + id
                + '\''
                + ", name='"
                + name
                + '\''

                + '}';
    }

    public Address(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Address() {
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
