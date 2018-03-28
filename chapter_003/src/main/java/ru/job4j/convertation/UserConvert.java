package ru.job4j.convertation;

import java.util.HashMap;
import java.util.List;

public class UserConvert {
    public HashMap<Integer, User> process(List<User> list) {
        HashMap<Integer, User> result = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            result.put(list.get(i).getId(), list.get(i));
        }
        return result;
    }
}
