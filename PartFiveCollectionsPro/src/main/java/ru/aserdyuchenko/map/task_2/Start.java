package ru.aserdyuchenko.map.task_2;

import ru.aserdyuchenko.map.task_1.User;
import java.util.HashMap;
import java.util.Map;

/**
 * @author anton.
 */
class Start {
    private Map<User, Object> map = new HashMap<>();

    void add(User user, Object object) {
        map.put(user, object);
    }

    void showMap() {
        System.out.println(map);
    }
}
