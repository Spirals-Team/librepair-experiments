package ru.job4j.map;

import java.util.HashMap;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class MapNotOverrideEandH {
    private HashMap<User, Object> map = new HashMap<>();

    public void put(User user, Object object) {
        map.put(user, object);
    }

    public HashMap<User, Object> getMap() {
        return map;
    }
}
