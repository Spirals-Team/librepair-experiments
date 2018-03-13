package ru.job4j.loop;

/**
 * arithmetic sums of even numbers.
 *
 * @author Afonin Sergey (afonin1c@mail.ru)
 * @version 01
 * @since 09.10.2017
 */
public class Counter {

    /**
     * Method add.
     * @param start initial number.
     * @param finish ending number.
     * @return arithmetic sums of even numbers.
     */
    public int add(int start, int finish) {

        //return 30;
        int result = 0;

        for (int i = start; i <= finish; i++) {

            if (i % 2 != 1) {

                result = result + i;
            }
        }

        return result;
    }

}
