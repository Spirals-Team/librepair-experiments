package ru.job4j.chess;
/**
 * тестирование класса слон
 */

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SlonTest {

    /**
     * тестирование метода way правильный ход
     */
    @Test
    public void slonReturnwayValid() {
        Slon slon = new Slon(new Cell(2, 2));
        if (slon.equals(new Cell(2, 2))) {
            System.out.println("заработало");
        }
        Cell[] wayslon = slon.way(new Cell(2, 2), new Cell(5, 5));
        assertThat(wayslon[0].hashCode(), is(new Cell(2, 2).hashCode()));
    }
}