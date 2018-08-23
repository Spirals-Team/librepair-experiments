package ru.job4j.sync;
/**
 *Класс User.
 *@author ifedorenko
 *@since 21.03.2018
 *@version 1
 */
public class User {
    /**
     * @param id user
     */
    private int id;
    /**
     * @param money
     */
    private int amount;

    /**
     * Constructor.
     * @param id user
     * @param amount money
     */
    public User(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    /**
     * Getter id.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter id.
     * @param id user
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter amount.
     * @return amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Setter amount.
     * @param amount money
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Method equals.
     * @param o object
     * @return result
     */
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

    /**
     * Method hashCode.
     * @return id
     */
    @Override
    public int hashCode() {
        return id;
    }
}
