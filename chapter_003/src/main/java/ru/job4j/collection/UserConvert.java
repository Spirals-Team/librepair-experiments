package ru.job4j.collection;

import java.util.*;

/**
 * Класс UserConvert.
 */
public class UserConvert {
    /**
     * Метод конвертации из List в HashMap<Integer, User>.
     * @param list list.
     * @return HashMap<Integer, User>.
     */
    public HashMap<Integer, User> process(List<User> list) {
        HashMap<Integer, User> resault = new HashMap<>();
        Iterator<User> iterator = list.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user != null) {
                resault.put(user.getId(), user);
            }
        }
        return resault;
    }
}
