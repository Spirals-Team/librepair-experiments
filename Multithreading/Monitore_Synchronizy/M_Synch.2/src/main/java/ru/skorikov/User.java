package ru.skorikov;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 09.01.18
 * @ version: java_kurs_standart
 *
 * Класс пользователь.
 */
public class User {
    /**
     * ID пользователя.
     */
    private int id;
    /**
     * Счет пользователя.
     */
    private int amount;

    /**
     * Конструктор.
     *
     * @param id     ID.
     * @param amount счет.
     */
    public User(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    /**
     * Конструктор.
     * @param id ID.
     */
    public User(int id) {
        this.id = id;
    }

    /**
     * Получить счет.
     * @return счет.
     */
    int getAmount() {
        return amount;
    }

    /**
     * Задать счет.
     * @param amount счет.
     */
    void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return id == user.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
