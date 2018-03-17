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
public class UserOverrideEquals {
    /**
     * поле1.
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
    public UserOverrideEquals() {
        this.map = map;
        map.put(user1, new Object());
        map.put(user2, new Object());
    }

    /**
     * Получить хранилище.
     * @return хранилище.
     */
    public Map<User, Object> getMap() {
        return map;
    }

    /**
     * Run application.
     * @param args arguments
     */
    public static void main(String[] args) {
        UserOverrideEquals user = new UserOverrideEquals();

        System.out.println(user.map);
    }
}

