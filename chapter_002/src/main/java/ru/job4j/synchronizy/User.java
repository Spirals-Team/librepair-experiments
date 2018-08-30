package ru.job4j.synchronizy;

/**
 * Class User.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class User {

    private int id;
    private int amount;

    public User(final int id, final int amount) {
        this.id = id;
        this.amount = amount;
    }

    /**
     * Get id.
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Get amount.
     *
     * @return amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Set amount.
     *
     * @param amount
     */
    public void setAmount(final int amount) {
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

    @Override
    public String toString() {
        return String.format("User{id=%d, amount=%d}", id, amount);
    }
}
