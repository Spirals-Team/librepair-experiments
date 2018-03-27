package ru.job4j.collections.frogway;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 30.10.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class WayTest {
    /**
     * Метод для запуска программы  х:у точка старта, уУ:хХ пункт назначения.
     */
    @Test
    public void name() {
    Way way = new Way();
    way.start(7,  11, 10, 9);
    }
}