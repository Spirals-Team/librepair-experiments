package ru.job4j.map;

import java.util.HashMap;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class TestMap<T> {
    private HashMap<T, Object> map = new HashMap<>();

    public void put(T t, Object object) {
        map.put(t, object);
    }

    public HashMap<T, Object> getMap() {
        return map;
    }

}
