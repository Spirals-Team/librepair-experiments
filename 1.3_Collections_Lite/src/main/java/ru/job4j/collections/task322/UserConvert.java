package ru.job4j.collections.task322;

import java.util.HashMap;
import java.util.List;

/**
 * Class UserConvert.
 */
public class UserConvert {
    /**
     * Main method in class. Convert from List to HashMap.
     * @param list List with User
     * @return result convert
     */
    public HashMap<Integer, User> process(List<User> list) {
        HashMap<Integer, User> result = new HashMap<>();
        for (User user : list) {
            result.put(user.getId(), user);
        }
        return result;
    }
}
