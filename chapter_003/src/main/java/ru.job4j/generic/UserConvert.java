package ru.job4j.generic;

import java.util.HashMap;
import java.util.List;

//Создать класс User с полями id, name, city.
//
//        Cоздать клаcc UserConvert.
//
//        В классе UserConvert написать метод public HashMap<Integer, User> process(List<User> list) {}, который принимает в себя список пользователей и конвертирует его в Map с ключом Integer id и соответствующим ему User.
public class UserConvert {

    public HashMap<Integer, User> process(List<User> list) {

        HashMap<Integer, User> userHashMap = new HashMap<Integer, User>() {

        };

        for (User user : list) {
            userHashMap.put(user.getId(), user);
        }

        return userHashMap;
    }
}