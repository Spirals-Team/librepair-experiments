package ru.job4j.multithreading.bomber.console;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 20.11.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class RandomOutput implements Output {

    @Override
    public int getRandomInt(int from, int to) {

        return (int) (from + Math.random() * (to - from));
    }

}
