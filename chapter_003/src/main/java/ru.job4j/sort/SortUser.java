package ru.job4j.sort;

import java.util.*;

public class SortUser {

    public TreeSet<User> sort(List<User> userList) {

//        userList.sort(new Comparator<User>() {
//            @Override
//            public int compare(User o1, User o2) {
//                return User;
//            }
//        });

        TreeSet<User> userTreeSet = new TreeSet<User>();

//

//        userTreeSet.addAll(userList);

        for (User user : userList) {
            userTreeSet.add(user);
        }

        return userTreeSet;
//        }
    }

    //    2. Сортировка User с использованием Comparator [#10036]
//    1) public List<User> sortNameLength (List<User>) - в этом методе необходимо определить
//    Comparator для метода Collections.sort и отсортировать List<User> по длине имени.
    public List<User> sortNameLength(List<User> userList) {
        userList.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
//                if (o1.getName().length() > o2.getName().length()) {
//                    return 1;
//                } else if (o1.getName().length() < o2.getName().length()) {
//                    return -1;
//                } else {
//                    return 0;
//                }
                return conditionForNameLength(o1, o2);
            }
        });
        return  userList;
    }

    //    2) public List<User> sortByAllFields (List<User>) - в этом методе необходимо определить Comparator для метода
//    Collections.sort и отсортировать List<User> по 2-м полям, сначала проверка по имени, потом по возрасту.
//    Например
//
//    Сергей, 25 (лет)
//    Иван, 30
//    Сергей, 20
//    Иван, 25
//
//    должно отсортироваться в
//    Иван 25
//    Иван 30
//    Сергей 20
//    Сергей 25
//в методе sortByAllFields() повторяющийся код. Вызывай метод sortNameLength()
    public List<User> sortByAllFields(List<User> userList) {
        userList.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
//                if (o1.getName().length() > o2.getName().length()) {
//                    return 1;
//                } else if (o1.getName().length() < o2.getName().length()) {
//                    return -1;
                int conditionForNameLength = conditionForNameLength(o1, o2);

                if (conditionForNameLength != 0) {
                        return conditionForNameLength;
                } else {
                    if (o1.getAge() > o2.getAge()) {
                        return 1;
                    } else if (o1.getAge() < o2.getAge()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            }
        });
        return userList;
    }

    int conditionForNameLength(User o1, User o2) {

        if (o1.getName().length() > o2.getName().length()) {
            return 1;
        } else if (o1.getName().length() < o2.getName().length()) {
            return -1;
        } else {
            return 0;
        }
    }




}
