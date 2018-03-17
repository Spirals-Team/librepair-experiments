package ru.job4j.litle.converter;

import java.util.HashMap;
import java.util.List;

/**
 * UserConvert.
 * @author Hincu Andrei (andreih1981@gmail.com) by 17.09.17;
 * @version $Id$
 * @since 0.1
 */
public class UserConvert {
    /**
     *Метод принимает в себя список пользователей
     * и конвертирует его в Map с ключом Integer id и соответствующим ему User.
     * @param list список пользователей.
     * @return Map с ключом id и значением соответствующего User.
     */
    public HashMap<Integer, User> process(List<User> list) {
    HashMap<Integer, User> map = new HashMap<>();
        for (User user : list) {
            int key = user.getId();
            map.put(key, user);
        }

    return map;
    }
}
