package ru.job4j.burse.comparator;

import ru.job4j.burse.Form;

import java.util.Comparator;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class Ascending implements Comparator<Form> {
    @Override
    public int compare(Form o1, Form o2) {
        double diff = o1.getPrice() - o2.getPrice();
        int result = 0;
        if (diff > 0) {
            result = 1;
        }
        if (diff < 0) {
            result = -1;
        }
        return result;
    }
}
