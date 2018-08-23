package ru.job4j.chess;


import org.junit.Test;
import ru.job4j.chess.figure.Bishop;
import ru.job4j.chess.figure.Figure;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
* Test chess.
* @author Igor Fedorenko (mailto:if.zommy@gmail.com)
* @version $Id$
* @since 0.1
*/
public class BoardTest {
    /**
     * Test ImpossibleMoveException.
     */
    @Test
    public void whenSetWrongDestinationCellThenGetImpossibleMoveException() {
        Board board = new Board();
        board.init();
        Cell source = board.findCellByName("D2");
        Cell destination = board.findCellByName("D5");
        try {
            board.move(source, destination);
        } catch (ImpossibleMoveException ime) {
            System.out.println("Error");
        }
    }

    /**
     * Test right work.
     */
    @Test
    public void wenSetRightDestinationCellThenPawnCanGo() {
        Board board = new Board();
        board.init();
        Cell source = board.findCellByName("D2");
        Cell destination = board.findCellByName("D4");
        boolean expect = destination.getFigure() instanceof Figure; //false, фигуры еще нет.
        board.move(source, destination);
        boolean result = destination.getFigure() instanceof Figure; //true, фигура передвинулась.
        assertThat(result, is(!expect));
    }

    /**
     * Test OccupiedWayException.
     */
    @Test
    public void whenSetWrongWayThenGetOccupiedWayException() {
        Board board = new Board();
        board.init();
        Cell source = board.findCellByName("C1");
        Cell destination = board.findCellByName("E3");
        try {
            board.move(source, destination);
        } catch (OccupiedWayException owe) {
            System.out.println("Error");
        }
    }
    /**
     * Test right result.
     */
    @Test
    public void whenPawnGoThenBishopCanGoToo() {
        Board board = new Board();
        board.init();
        Cell sourceP = board.findCellByName("D2");
        Cell destinationP = board.findCellByName("D4");
        Cell sourceB = board.findCellByName("C1");
        Cell destinationB = board.findCellByName("E3");
        board.move(sourceP, destinationP);
        board.move(sourceB, destinationB);
        assertThat(destinationB.getFigure() instanceof Bishop, is(true));
    }
    /**
     * Test BoardOutException.
     */
    @Test
    public void whenChooseCellOutOfBoardThenBoardOutException() {
        Board board = new Board();
        board.init();
        try {
            board.findCellByName("A0");
        } catch (BoardOutException boe) {
            System.out.println("Error");
        }
    }
    /**
     * Test right result.
     */
    @Test
    public void whenChooseCellInTheBoardThenCellExist() {
        Board board = new Board();
        board.init();
        String result = board.getBoard()[7][0].getName();
        String expect = "A1";
        assertThat(result, is(expect));
    }
    /**
     * Test FigureNotFoundException.
     */
    @Test
    public void whenChooseCellWithoutFiguresThenGetFigureNotFoundException() {
        Board board = new Board();
        board.init();
        Cell sourceP = board.findCellByName("D3");
        Cell destinationP = board.findCellByName("D4");
        try {
            board.move(sourceP, destinationP);
        } catch (FigureNotFoundException fnfe) {
            System.out.println("Error");
        }
    }
}
