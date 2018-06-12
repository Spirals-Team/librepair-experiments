package ru.job4j;

import java.util.HashMap;
import java.util.List;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class UserConvert {
    public HashMap<Integer, User> process(List<User> list) {
        HashMap<Integer, User> user = new HashMap<>();
        for (User u : list) {
            user.put(u.getId(), u);
        }
        return user;
    }
}
