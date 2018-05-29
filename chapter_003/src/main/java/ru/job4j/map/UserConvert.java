package ru.job4j.map;

import ru.job4j.user.User;

import java.util.HashMap;
import java.util.List;

public class UserConvert {

    public HashMap<Integer, User> process(List<User> list) {
        HashMap<Integer, User> result = new HashMap<>();
        for (User users: list) {
            result.put(users.getId(), users);
        }
        return result;
    }
}
