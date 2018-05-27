package ru.job4j.coffeemachine;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class Automat {
    private int[] money = {10, 5, 2, 1};
    public int[] changes(int value, int price) throws NotEnoughMoney {
        int balance = value - price;
        if (balance < 0) {
            throw new NotEnoughMoney("Not enough money.");
        }
        int[] massBalance = {};
        int indexMoney = 0;
        do {
            if (balance - money[indexMoney] >= 0) {
                balance -= money[indexMoney];
                massBalance = massAdd(massBalance, money[indexMoney]);
            } else if (balance == 0) {
                massBalance = massAdd(massBalance, 0);
            } else {
                indexMoney++;
            }
        } while (balance != 0);
        return massBalance;
    }

    private int[] massAdd(int[] massBalance, int newItem) {
        int[] newMassBalance = new int[massBalance.length + 1];
        for (int i = 0; i < massBalance.length; i++) {
            newMassBalance[i] = massBalance[i];
        }
        newMassBalance[massBalance.length] = newItem;
        return newMassBalance;
    }
}
