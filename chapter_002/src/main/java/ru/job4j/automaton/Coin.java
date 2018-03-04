package ru.job4j.automaton;
/**
 * Coin.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class Coin {
    /*** Value of coin.*/
    private int value;
    /*** Quantity of coins.*/
    private int quantity;
    /**
     * Constructor.
     * @param value - value
     * @param quantity - quantity
     */
    public Coin(int value, int quantity) {
        this.value = value;
        this.quantity = quantity;
    }
    /**
     * Get value.
     * @return - value
     */
    public int getValue() {
        return value;
    }
    /**
     * Get quantity.
     * @return - quantity
     */
    public int getQuantity() {
        return quantity;
    }
    /**
     * Set quantity.
     * @param quantity - quantity
     */
    void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
