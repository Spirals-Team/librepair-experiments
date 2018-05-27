package ru.job4j.sql;

import javax.xml.bind.annotation.*;
import java.util.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
@XmlRootElement(name = "entries")
public class MyEntries {
    @XmlElement(name = "entry")
    private List<MyEntry> myList = new LinkedList<>();

    public void addInList(MyEntry myEntry) {
        myList.add(myEntry);
    }
}
