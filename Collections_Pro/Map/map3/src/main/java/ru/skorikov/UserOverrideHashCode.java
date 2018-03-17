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
public class UserOverrideHashCode {
    /**
     * Поле1.
     */
    private User user1 = new User("Name1", 2, 1, 2, 1990);
    /**
     * Поле2.
     */
    private User user2 = new User("Name1", 2, 1, 2, 1990);
    /**
     * Хранилище.
     */
    private Map<User, Object> map = new HashMap<>();

    /**
     * Конструктор.
     */
    public UserOverrideHashCode() {
        this.map = map;
        map.put(user1, new Object());
        map.put(user2, new Object());
    }

    /**
     * Получить харанилище.
     * @return хранилище.
     */
    public Map<User, Object> getMap() {
        return map;
    }

    /**
     * Метод запуска приложения.
     * @param args массив строк.
     */
    public static void main(String[] args) {
        UserOverrideHashCode user = new UserOverrideHashCode();

        System.out.println(user.map);
    }
}
