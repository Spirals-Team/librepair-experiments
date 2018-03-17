package ru.skorikov;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 05.03.18
 * @ version: java_kurs_standart
 */
public class HeroTest {
    /**
     * Создадим героя и запустим его.
     */
    @Test
    public void tryCreateHero() {
        Board board = new Board(2, 1);
        new Thread(new Hero("Name", board)).start();
    }
}