package ru.job4j.bankomat;
/**
 * тест банкомат
 */

import org.junit.Test;
import ru.job4j.bankomat.Bankomat;

public class BankomatTest {
    @Test
    public void bankamatTestirovanie() {
        int value = 25;
        int price = 35;
        int[] sdacha = new Bankomat().changes(value, price);
        for (int test : sdacha
                ) {
            if (test != 0) {
                System.out.println(test);
            }
        }
    }

}