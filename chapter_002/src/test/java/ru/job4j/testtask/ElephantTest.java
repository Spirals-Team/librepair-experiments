package ru.job4j.testtask;

import org.junit.Test;
import ru.job4j.testtask.figures.Elephant;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
/**
 * Test.
 * @author Hincu Andrei (andreih1981@gmail.com) by 11.09.17;
 * @version $Id$
 * @since 0.1
 */
public class ElephantTest {
    /**
     * вверх и в лево.
     * @throws Exception ерор.
     */
    @Test
    public void whenElephantWay() throws Exception {
        Elephant elephant = new Elephant("Белый", new Cell(5, 5));
        Cell[] rezult2 = elephant.way(new Cell(3, 7));
        Cell[]ex2 = {new Cell(4, 6), new Cell(3, 7)};
        assertThat(rezult2, is(ex2));
    }

    /**
     * вверх и вправо.
     * @throws Exception ерор.
     */
    @Test
    public void whenElephantWay1() throws Exception {
        Elephant elephant = new Elephant("Белый", new Cell(5, 5));
        Cell[] rezult = elephant.way(new Cell(7, 7));
        Cell[]ex = {new Cell(6, 6), new Cell(7, 7)};
        assertThat(rezult, is(ex));
    }

    /**
     * вниз и влево.
     * @throws Exception ерор.
     */
    @Test
    public void whenElephantWay3() throws Exception {
        Elephant elephant = new Elephant("Белый", new Cell(5, 5));
        Cell[] rezult1 = elephant.way(new Cell(3, 3));
        Cell[]ex1 = {new Cell(4, 4), new Cell(3, 3)};
        assertThat(rezult1, is(ex1));
    }

    /**
     * вниз и вправо.
     * @throws Exception ерор.
     */
    @Test
    public void whenElephantWay4() throws Exception {
        Elephant elephant = new Elephant("Белый", new Cell(5, 5));
        Cell[] rezult1 = elephant.way(new Cell(7, 3));
        Cell[]ex1 = {new Cell(6, 4), new Cell(7, 3)};
        assertThat(rezult1, is(ex1));
    }

    /**
     * isOnCell.
     * @throws Exception error.
     */
    @Test
    public void whenIsOnCell() throws Exception {
        Elephant elephant = new Elephant("Белый", new Cell(3, 1));
        boolean result = elephant.isOneCell(new Cell(3, 1));
        boolean ex = true;
        assertThat(result, is(ex));
    }
    @Test
    public void whenIsOnCellE() {
        Elephant elephant = new Elephant("Белый", new Cell(3, 1));
        Cell cell = new Cell(4, 2);
        elephant.clonE(cell);
        assertEquals(elephant.isOneCell(cell), true);
    }
}
