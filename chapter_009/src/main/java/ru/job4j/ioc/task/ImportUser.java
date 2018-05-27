package ru.job4j.ioc.task;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class ImportUser {
    public static void main(String[] args) {
        Storage<User> storage = new JdbcStorage("jdbc:postgresql://localhost:5432/spring", "postgres", "Qwerty123", true);
        User userOne = new User("TestOne");
        User userTwo = new User("TestTwo");
        User userThree = new User("TestThree");
        storage.add(userOne);
        storage.add(userTwo);
        storage.add(userThree);
        userOne = storage.find(userOne.getName());
        userTwo = storage.find(userTwo.getName());
        userThree = storage.find(userThree.getName());
        System.out.println(storage.getUsers());
        System.out.println("user one name: " + storage.find(userOne.getName()));
        System.out.println("user two id: " + storage.find(userTwo.getId()));
        System.out.println("user three: " + storage.find(userThree));
        System.out.println(storage.del(userOne.getId()));
        System.out.println(storage.getUsers());
        System.out.println(storage.del(userTwo));
        System.out.println(storage.getUsers());

    }
}
