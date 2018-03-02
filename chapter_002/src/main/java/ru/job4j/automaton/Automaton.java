package ru.job4j.automaton;

/**
 * Automaton.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class Automaton {
    /*** Array of coins.*/
    private Coin[] coins = {new Coin(1, 10), new Coin(2, 10), new Coin(5, 10), new Coin(10, 10)};
    /**
     * Get coins.
     * @return - coins
     */
    public Coin[] getCoins() {
        return this.coins;
    }
    /**
     * Add coin.
     * @param coin - coin
     */
    public void addCoin(Coin coin) {
        for (Coin cn : this.coins) {
            if (cn.getValue() == coin.getValue()) {
                cn.setQuantity(cn.getQuantity() + coin.getQuantity());
            }
        }
    }
    /**
     * Cost of all coins.
     * @param coins - coins array
     * @return - int
     */
    private int costOfAllCoins(Coin[] coins) {
        int allCoins = 0;
        for (Coin coin : coins) {
            allCoins += coin.getValue() * coin.getQuantity();
        }
        return allCoins;
    }
    /**
     * Enough coins in automaton.
     * @param buyersCoins - quantity of coins
     * @param price - price
     * @return - true or false
     */
    private boolean enoughCoins(int price, Coin[] buyersCoins) {
        return this.costOfAllCoins(this.coins) >= this.costOfAllCoins(buyersCoins) - price;
    }
    /**
     * Buy.
     * @param priceStuff - price
     * @param coins - coins
     * @return - string
     */
    public String buy(int priceStuff, Coin[] coins) {

        if (priceStuff > this.costOfAllCoins(coins)) {
            return "Not enough money for buy!!!";
        }

        if (!this.enoughCoins(priceStuff, coins)) {
            return "Not enough coins for change!!!";
        }

        for (Coin coin : coins) {
            this.addCoin(coin);
        }

        int balance = this.costOfAllCoins(coins) - priceStuff;
        int moreValue = -1;
        while (balance > 0) {
            Coin maxValuesCoin = new Coin(0, 0);
            for (Coin coin : this.coins) {
                if (coin.getValue() > maxValuesCoin.getValue()) {
                    if (coin.getQuantity() == 0 || coin.getValue() == moreValue) {
                        continue;
                    }
                    maxValuesCoin = coin;
                }
            }
            if (balance - maxValuesCoin.getValue() >= 0) {
                balance -= maxValuesCoin.getValue();
                maxValuesCoin.setQuantity(maxValuesCoin.getQuantity() - 1);
            } else {
                moreValue = maxValuesCoin.getValue();
            }

        }
        return "Change: " + (this.costOfAllCoins(coins) - priceStuff);
    }
}
