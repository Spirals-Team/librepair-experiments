package ru.job4j.coffe;

import java.util.ArrayList;

/**
 * Класс, описывающий кофейный аппарат.
 * @author Alex Rumyantcev
 * @version $Id$
 */
public class CoffeMachine {

    // массив номинала монет для сдачи в порядке убывания
    private static final int[] COINS = {10, 5, 2, 1};

    /**
     * Метод, считающий сдачу в монетках доступного номинала.
     * @param value сумма, закуинутая в аппарат
     * @param price стоимость покупки
     * @return массив монеток для сдачи
     */
    public ArrayList changes(final int value, final int price) throws NoMoneyException {
        ArrayList<Integer> change = new ArrayList<>();
        int ostatok = value - price;
        if (ostatok > 0) {
            for (int coin : COINS) {
                int celoe = ostatok / coin;
                while (celoe-- > 0) {
                    change.add(coin);
                    ostatok -= coin;
                }
            }
        } else if (ostatok < 0) {
            throw new NoMoneyException();
        }
        return  change;
    }
}

