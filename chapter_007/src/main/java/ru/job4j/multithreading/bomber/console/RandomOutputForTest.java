package ru.job4j.multithreading.bomber.console;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 24.11.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class RandomOutputForTest implements Output {
    private int[]values = {10, 5, 5, 5};
    private int position = 0;

    @Override
    public int getRandomInt(int from, int to) {
        return values[position++];
    }
}
