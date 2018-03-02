package ru.job4j.userconvert;

import java.util.HashMap;
import java.util.List;
/**
 * UserConvert.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class UserConvert {
    /**
     * Convert list of users to HashMap.
     * @param list - list of users
     * @return - HashMap
     */
    public HashMap<Integer, User> process(List<User> list) {
        HashMap<Integer, User> hashMap = new HashMap<>();
        for (User user : list) {
            hashMap.put(user.getId(), user);
        }
        return hashMap;
    }
}
