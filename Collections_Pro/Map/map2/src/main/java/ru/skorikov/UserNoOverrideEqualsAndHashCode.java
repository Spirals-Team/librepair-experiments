package ru.skorikov;


import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 07.11.17
 * @ version: java_kurs_standart
 */
public class UserNoOverrideEqualsAndHashCode {
    /**
     * Поле1.
     */
    private User user1 = new User("Name1", 2, 1, 2, 1990);
    /**
     * Поле2.
     */
    private User user2 = new User("Name1", 2, 1, 2, 1990);

    /**
     * Конструктор.
     */
    public UserNoOverrideEqualsAndHashCode() {
        this.mapUser = mapUser;
        mapUser.put(user1, new Object());
        mapUser.put(user2, new Object());
    }

    /**
     * Хранилище юзеров.
     */
    private Map<User, Object> mapUser = new HashMap<>();

    /**
     * Получить карту.
     * @return карта.
     */
    public Map<User, Object> getMapUser() {
        return mapUser;
    }

    /**
     * Метод запуска приложения.
     * @param args массив строк.
     */
    public static void main(String[] args) {
        UserNoOverrideEqualsAndHashCode user = new UserNoOverrideEqualsAndHashCode();

        System.out.println(user.mapUser);
    }
}
