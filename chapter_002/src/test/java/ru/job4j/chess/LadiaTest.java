package ru.job4j.chess;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class LadiaTest {
    @Test
    public void ladiaReturnwayValid() {
        Ladia ladia = new Ladia(new Cell(2, 2));

        Cell[] wayladia = ladia.way(new Cell(2, 2), new Cell(2, 5));
        assertThat(wayladia[3].hashCode(), is(new Cell(2, 5).hashCode()));
    }

}