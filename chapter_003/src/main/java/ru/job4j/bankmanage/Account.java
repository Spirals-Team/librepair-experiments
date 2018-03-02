package ru.job4j.bankmanage;

import java.util.Random;
/**
 * Account.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class Account {
    /*** Amount of money.*/
    private double value;
    /*** Account requisites.*/
    private String requisites;
    /*** Random.*/
    private static final Random RN = new Random();
    /**
     * Generate requisites of account.
     * @return string of requisites
     */
    private String generateRequisites() {
        return String.valueOf(System.currentTimeMillis() + RN.nextInt());
    }
    /**
     * Constructor.
     * @param value - amount of money
     */
    public Account(double value) {
        this.value = value;
        this.requisites = this.generateRequisites();
    }
    /**
     * Get value.
     * @return - value
     */
    public double getValue() {
        return value;
    }
    /**
     * Set value.
     * @param value - value
     */
    public void setValue(double value) {
        this.value = value;
    }
    /**
     * Get requisites.
     * @return - requisites
     */
    public String getRequisites() {
        return requisites;
    }
}
