package ru.job4j.coffeemachine.vavealgortim;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TicTacToeTest {

    @Test
    public void whenHasNoWinnerThenFalse() {

//        x  0	0
//        0	 x	x
//        x  x  0

        int[][] values = new int[][]{{1, 0, 0}, {0, 1, 1}, {1, 1, 0}};

        TicTacToe ticTacToe = new TicTacToe(values);

        boolean result = ticTacToe.hasWinner();
        boolean expectedresult = false;
        assertThat(result, is(expectedresult));
    }

    @Test
    public void whenHasWinnerThenTrue() {

//        x  0	0
//        0	 x	x
//        x  0  x

        int[][] values = new int[][]{{1, 0, 0}, {0, 1, 1}, {1, 0, 1}};

        TicTacToe ticTacToe = new TicTacToe(values);

        boolean result = ticTacToe.hasWinner();
        boolean expectedresult = true;
        assertThat(result, is(expectedresult));
    }

}