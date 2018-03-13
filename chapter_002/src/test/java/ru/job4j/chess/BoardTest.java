package ru.job4j.chess;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class BoardTest {

    @Test
    public void whenConstructBoardSize2ThenWeHaveBoardSize2() {

        Board board = new Board(2);

        Cell[] result = board.cells;

        Cell[] expected = {new Cell(1, 1), new Cell(1, 2), new Cell(2, 1), new Cell(2, 2)};

        assertThat(result, is(expected));
    }

    @Test
    public void whenConstructBoardSize3ByThenWeHaveBoardSize3By3() {

        Board board = new Board(3);

        Cell[] result = board.cells;

        Cell[] expected = {new Cell(1, 1), new Cell(1, 2), new Cell(1, 3),
                            new Cell(2, 1), new Cell(2, 2), new Cell(2, 3),
                                new Cell(3, 1), new Cell(3, 2), new Cell(3, 3)};

        assertThat(result, is(expected));
    }

    @Test
    public void whenConstructBoardSize4By4ThenWeHaveBoardSize4By4() {

        Board board = new Board(4);

        Cell[] result = board.cells;

        Cell[] expected = {new Cell(1, 1), new Cell(1, 2), new Cell(1, 3), new Cell(1, 4),
                new Cell(2, 1), new Cell(2, 2), new Cell(2, 3), new Cell(2, 4),
                new Cell(3, 1), new Cell(3, 2), new Cell(3, 3), new Cell(3, 4),
                new Cell(4, 1), new Cell(4, 2), new Cell(4, 3), new Cell(4, 4)};

        assertThat(result, is(expected));
    }

    @Test
    public void whenWeGoByAnElephantDiagonallyWeGetADiagonal() throws CloneNotSupportedException {

        Board board = new Board(4);

        FigureElephant figureElephant = new FigureElephant(new Cell(1, 1));

        Cell[] result = figureElephant.way(new Cell(4, 4));

        Cell[] expected = new Cell[]{new Cell(2, 2), new Cell(3, 3), new Cell(4, 4)};

        assertThat(result, is(expected));
    }

    @Test
    public void whenWeGoByAnElephantButWeDontNoThatItIsElephantDiagonallyWeGetADiagonal() throws CloneNotSupportedException {

        Board board = new Board(4);

        FigureElephant figureElephant = new FigureElephant(new Cell(1, 1));

        Figure figure = (Figure) figureElephant;

        Cell[] result = figure.way(new Cell(4, 4));

        Cell[] expected = new Cell[]{new Cell(2, 2), new Cell(3, 3), new Cell(4, 4)};

        assertThat(result, is(expected));
    }

    @Ignore
    @Test
    public void ifYouWalkWithAnElephantNotOnADiagonalYouGetAnError() throws ImposibleMoveException {

        Board board = new Board(4);

        FigureElephant figureElephant = new FigureElephant(new Cell(1, 1));

        Cell[] result = figureElephant.way(new Cell(4, 3));

    }

    @Test
    public void whenThereIsFigureinTheCellGetFigureFromTheCell() {

        Board board = new Board(4);

        FigureElephant figureElephant = new FigureElephant(new Cell(2, 2));

        board.addFigureToTheArrayOfFigures(figureElephant);

        Figure result = board.getFigureFromCell(new Cell(2, 2));

        Figure expected = figureElephant;

        assertThat(result, is(expected));

    }

    @Ignore
    @Test
    public void whenThereIsNoFigureInTheCellGetFigureNotFoundException() {

        Board board = new Board(4);

        FigureElephant figureElephant = new FigureElephant(new Cell(2, 2));

        board.addFigureToTheArrayOfFigures(figureElephant);

        boolean result = board.move(new Cell(1, 1), new Cell(3, 3));

        boolean expected = false;

        assertThat(result, is(expected));

    }

    @Ignore
    @Test
    public void whenWayOfTheFigureIsOccupiedThenGetOccupiedWayException() {

        Board board = new Board(4);

        FigureElephant figureElephant = new FigureElephant(new Cell(1, 1));
        board.addFigureToTheArrayOfFigures(figureElephant);

        FigureElephant figureElephantOccupidWay = new FigureElephant(new Cell(3, 3));
        board.addFigureToTheArrayOfFigures(figureElephantOccupidWay);

        boolean result = board.move(new Cell(1, 1), new Cell(4, 4));

    }

    @Test
    public void whenWayIsRightThenThePositionOfFigureIsChanged() {

        Board board = new Board(4);

        FigureElephant figureElephant = new FigureElephant(new Cell(1, 1));
        board.addFigureToTheArrayOfFigures(figureElephant);

        FigureElephant figureElephantOccupidWay = new FigureElephant(new Cell(2, 3));
        board.addFigureToTheArrayOfFigures(figureElephantOccupidWay);

        board.move(new Cell(1, 1), new Cell(4, 4));

        Cell result = board.getFigureFromCell(new Cell(4, 4)).position;
        Cell expected = new FigureElephant(new Cell(4, 4)).position;

//        boolean result = board.inThisCellThereIsAFigure(new Cell(4,4));
//        boolean expected = true;

        assertThat(result, is(expected));
    }
}