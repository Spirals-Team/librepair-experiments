package ru.job4j.chees;

import org.junit.Test;
import ru.job4j.chess.Board;
import ru.job4j.chess.Cell;
import ru.job4j.chess.Figure;
import ru.job4j.chess.exceptions.FigureNotFoundException;
import ru.job4j.chess.exceptions.ImpossibleMoveException;
import ru.job4j.chess.exceptions.OccupiedWayException;
import ru.job4j.chess.figures.Bishop;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
/**
 * Board test.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class BoardTest {
    /**
     * When the correct coordinates of cells.
     * @throws ImpossibleMoveException - if figure not move to coordinate
     * @throws OccupiedWayException - if way of occupied
     * @throws FigureNotFoundException - if figure not found
     */
    @Test
    public void whenCorrectCellsCoordinatesThenTrue() throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {
        Board board = new Board();
        Figure bishop = new Bishop(new Cell(0, 4));
        board.addFigure(bishop);
        boolean result = board.move(new Cell(0, 4), new Cell(2, 2));
        assertThat(result, is(true));
    }
    /**
     * When the wrong coordinates of figure.
     * @throws ImpossibleMoveException - if figure not move to coordinate
     * @throws OccupiedWayException - if way of occupied
     * @throws FigureNotFoundException - if figure not found
     */
    @Test (expected = FigureNotFoundException.class)
    public void whenWrongCoordinatesOfFigureThenFigureNotFoundException() throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {
        Board board = new Board();
        Figure bishop = new Bishop(new Cell(0, 4));
        board.addFigure(bishop);
        board.move(new Cell(4, 4), new Cell(3, 3));
    }
    /**
     * When a figure not move to coordinate.
     * @throws ImpossibleMoveException - if figure not move to coordinate
     * @throws OccupiedWayException - if way of occupied
     * @throws FigureNotFoundException - if figure not found
     */
    @Test (expected = ImpossibleMoveException.class)
    public void whenFigureNotMoveToCoordinatesThenImpossibleMoveException() throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {
        Board board = new Board();
        Figure bishop = new Bishop(new Cell(0, 4));
        board.addFigure(bishop);
        board.move(new Cell(0, 4), new Cell(3, 3));
    }
    /**
     * When way of occupied.
     * @throws ImpossibleMoveException - if figure not move to coordinate
     * @throws OccupiedWayException - if way of occupied
     * @throws FigureNotFoundException - if figure not found
     */
    @Test (expected = OccupiedWayException.class)
    public void whenWayOfOccupiedThenOccupiedWayException() throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {
        Board board = new Board();
        Figure bishop = new Bishop(new Cell(0, 4));
        Figure occupied = new Bishop(new Cell(1, 3));
        board.addFigure(bishop);
        board.addFigure(occupied);
        board.move(new Cell(0, 4), new Cell(2, 2));
    }
}
