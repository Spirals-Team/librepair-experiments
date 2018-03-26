package ru.job4j.jdbc;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

@XmlRootElement
public class Entries {
    private ArrayList<Integer> list = new ArrayList<>();

    @XmlElementWrapper(name = "entry")
    @XmlElement(name = "field")
    public ArrayList<Integer> getList() {
        return list;
    }
}
