package ru.job4j.convert;

import java.util.HashMap;
import java.util.List;

public class UserConvert {

    /**
     * Метод конвертирует список Пользователей в хешмэп
     * @param list список Пользователей
     * @return результирующий меп
     */
    public static HashMap<Integer, User> Process(List<User> list) {
        HashMap<Integer, User> result = new HashMap<>();
        for (User user:list) {
            result.put(user.getId(), user);
        }
        return result;
    }
}
