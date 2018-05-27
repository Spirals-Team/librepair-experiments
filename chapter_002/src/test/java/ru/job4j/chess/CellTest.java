package ru.job4j.chess;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class CellTest {

    @Test
    public void whenGetEqualsBetweenTwoCell() {
        boolean expect = true;
        Cell cell = new Cell(1, 2);
        assertThat(new Cell(1, 2).equals(cell), is(expect));
    }

    @Test
    public void whenGetX() {
        int expect = 1;
        Cell cell = new Cell(1, 2);
        int rsl = cell.getX();
        assertThat(rsl, is(expect));
    }

    @Test
    public void whenGetY() {
        int expect = 1;
        Cell cell = new Cell(2, 1);
        int rsl = cell.getY();
        assertThat(rsl, is(expect));
    }
}