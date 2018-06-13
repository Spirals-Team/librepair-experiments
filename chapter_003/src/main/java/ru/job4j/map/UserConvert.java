package ru.job4j.map;

import java.util.HashMap;
import java.util.List;

public class UserConvert {

    /**
     * Метод конвертирует список пользователей в Map по ключу int id.
     */
    public HashMap<Integer, User> process(List<User> list) {
        HashMap<Integer, User> result = new HashMap<>();
        for (User index : list) {
            result.put(index.getId(), index);
        }
        return result;
    }
}
