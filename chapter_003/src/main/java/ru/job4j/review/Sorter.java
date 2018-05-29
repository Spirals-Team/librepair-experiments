package ru.job4j.review;


import ru.job4j.user.User;

import java.util.*;

public class Sorter {

    /**
     * Конструктор для класса Sorter.
     * В классе Sorter уже определен неявный конструктор по умолчанию.
     * Наличие данного конструктора при отсутсвии других явных конструкторов не обязательно.
     */
    public Sorter() {
    }

    /**
     * Мктод добавления типа данных из коллекции лист в колекцию Set.
     * Для  создания коллеции из другой коллекции достаточно воспользоваться
     * ключевым словом new, названием коллекции и использованием типа данных в дженерике коллекции.
     *
     * @param list Входная коллекция List с типом даннхы User
     * @return возвращает коллецию TreeSet с данными из коллекции User
     */
    Set<User> sort(List<User> list) {
        return new TreeSet<>(list);
        /*
       TreeSet<User> sortedList = new TreeSet<>();
       sortedList.addAll(list);
       return sortedList;
         */

    }

    /**
     * Метод сортировки по длинне имени в коллекции.
     * Для сортировки при помощи компаратора достаточно воспользоваться
     * анонимным классом, в котором создаем комппаратор и переопределям его метод.
     *
     * @param list Входная коллекция.
     * @return Возвращает отсортированную компаратором коллекцию.
     */
    List<User> sortnamelength(List<User> list) {
        list.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getName().length() - o2.getName().length();
            }
        });
        return list;
          /*
          Comparator<User> compar = new Comparator<User>() {
            @Override
            public int compare (User o1, User o2) {
                return o1.getName().length() - o2.getName().length();
            }
        };
        list.sort(compar);
        return list;
         */
    }

    /**
     * Метод сортировки по двум полям.
     * Для сортировки по всем поялм достаточно использовать один компаратор.
     * @param list входная коллекция.
     * @return отсортированная коллекция.
     */
    List<User> sortboth(List<User> list) {
/*

        Comparator<User> compar1 = new Comparator<User>() {
            @Override
            public int compare (User o1, User o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
        Comparator<User> compar2 = new Comparator<User>() {
            @Override
            public int compare (User o1, User o2) {
                return o1.getAge() - o2.getAge();
            }
        };
        list.sort(compar1.thenComparing(compar2));
        return list;
    }
 */
        list.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return list;
    }
}