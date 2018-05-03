package ru.job4j.convertlistinmap;
/**
 * хотел сделать чрез итератор, но показалось что так будет на много короче,
 * очень понравился пример, из обучающего видеоролика
 * Метод перекидывает List в HashMap
 */

import java.util.HashMap;
import java.util.List;

public class UsersConvert {
    public HashMap<Integer, User> process(List<User> list) {
        HashMap<Integer, User> resultHashmap = new HashMap<>();
        list.forEach(user -> resultHashmap.put(user.getId(), user));
        return resultHashmap;
    }
}
