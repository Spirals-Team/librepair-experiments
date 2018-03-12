package ru.job4j.sort;

/**
 * Класс User.
 */
public class User implements Comparable<User> {
    /**
     * Переменная name. Имя.
     */
    private String name;
    /**
     * Переменная age. Возрост.
     */
    private int age;

    /**
     * Конструктор для объекта.
     *
     * @param name имя.
     * @param age  возраст.
     */
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * Геттер для имени.
     *
     * @return вернем имя.
     */
    public String getName() {
        return name;
    }

    /**
     * Геттре для возраста.
     *
     * @return вернем возраст.
     */
    public int getAge() {
        return age;
    }

    @Override
    public int compareTo(User o) {
        final int rsl = Integer.compare(this.age, o.age);
        return rsl != 0 ? rsl : -1;
    }
}
