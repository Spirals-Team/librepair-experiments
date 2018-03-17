package ru.job4j.bankomat;

import java.util.Arrays;

/**
 * апарат по выдаче сдачи.
 * @author Hincu Andrei (andreih1981@gmail.com)on 10.11.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class Avtomat {
    /**
     * значения купюр которыми распологает аппарат.
     */
    private final int[]banknote = {10, 5, 2, 1};

    /**
     * Метод рассчитывает сдачу.
     * @param value сумма внесенная.
     * @param price цена.
     * @return массив купюр.
     */
    int[] changes(int value, int price) {
        int position = 0;
        int[] change = {0};
        if (value != price) {
            int ch = value - price;
            for (int aBanknote : banknote) {
                int count = ch / aBanknote;
                ch = ch % aBanknote;
                if (count > 0) {
                    change = Arrays.copyOf(change, change.length  + count);
                    for (int i = 0; i < count; i++) {
                        change[position++] = aBanknote;
                    }
                }
            }
        }
        return Arrays.copyOf(change, position);
    }
}
