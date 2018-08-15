package ru.job4j.chess;

import ru.job4j.chess.figure.Figure;
import ru.job4j.chess.figure.Bishop;
import ru.job4j.chess.figure.Castle;
import ru.job4j.chess.figure.Pawn;
import ru.job4j.chess.figure.Queen;
import ru.job4j.chess.figure.Knight;
import ru.job4j.chess.figure.King;

/**
 *Class Board.
 *@author ifedorenko
 *@since 03.09.2017
 *@version 1
 */
public class Board {
    /**
     * @param board our chess board.
     */
    private Cell[][] board;

    /**
     * @param figures our array of figures.
     */
    private Figure[] figures;

    /**
     * @param x column
     */
    private final int x = 8;

    /**
     *@param y row
     */
    private final int y = 8;

    /**
     * Create board.
     */
    private void createBoard() {
        this.board = new Cell[x][y];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if ((i + j) % 2 == 0) {
                    board[i][j] = new Cell(this, (char) ('A' + j), (char) ('1' + board.length - 1 - i), "white");
                } else {
                    board[i][j] = new Cell(this, (char) ('A' + j), (char) ('1' + board.length - 1 - i), "black");
                }
            }
        }
    }

    /**
     * Create chee figures.
     */
    private void createChess() {
        this.figures = new Figure[32];
        int j = 0;
        for (int i = 0; i < 8; i++) {
            this.figures[j++] = new Pawn(findCellByXY((char) ('A' + i), '2'), "white");
        }
        for (int i = 0; i < 8; i++) {
            this.figures[j++] = new Pawn(findCellByXY((char) ('A' + i), '7'), "black");
        }
        this.figures[j++] = new Castle(this.findCellByName("A1"), "white");
        this.figures[j++] = new Castle(this.findCellByName("A8"), "black");
        this.figures[j++] = new Castle(this.findCellByName("H1"), "white");
        this.figures[j++] = new Castle(this.findCellByName("H8"), "black");

        this.figures[j++] = new Knight(this.findCellByName("B1"), "white");
        this.figures[j++] = new Knight(this.findCellByName("B8"), "black");
        this.figures[j++] = new Knight(this.findCellByName("G1"), "white");
        this.figures[j++] = new Knight(this.findCellByName("G8"), "black");

        this.figures[j++] = new Bishop(this.findCellByName("C1"), "white");
        this.figures[j++] = new Bishop(this.findCellByName("C8"), "black");
        this.figures[j++] = new Bishop(this.findCellByName("F1"), "white");
        this.figures[j++] = new Bishop(this.findCellByName("F8"), "black");

        this.figures[j++] = new Queen(this.findCellByName("D1"), "white");
        this.figures[j++] = new Queen(this.findCellByName("D8"), "black");

        this.figures[j++] = new King(this.findCellByName("E1"), "white");
        this.figures[j++] = new King(this.findCellByName("E8"), "black");
    }

    /**
     * Initialize our board.
     */
    public void init() {
        this.createBoard();
        this.createChess();
    }

    /**
     * Find cell by Name.
     * @param name name of cell.
     * @return cell.
     * @throws BoardOutException out of board range
     */
    public Cell findCellByName(String name) throws BoardOutException {
        Cell result = null;
        char[] source = name.toCharArray();
        if (source[0] >= 'A' && source[0] <= 'H' && source[1] >= '1' && source[1] <= '8') {
            for (Cell[] cells : this.board) {
                for (Cell cell : cells) {
                    if (name.equals(cell.getName())) {
                        result = cell;
                    }
                }
            }
        } else {
            throw new BoardOutException("out of board range.");
        }
        return result;
    }

    /**
     * Find cell by XY.
     * @param x param x of cell
     * @param y param y of cell
     * @return cell
     * @throws BoardOutException out of board range
     */
    public Cell findCellByXY(char x, char y) throws BoardOutException {
        Cell result = null;
        if (x >= 'A' && x <= 'H' && y >= '1' && y <= '8') {
            for (Cell[] cells : this.board) {
                for (Cell cell : cells) {
                    if (x == cell.getX() && y == cell.getY()) {
                        result = cell;
                    }
                }
            }
        } else {
            throw new BoardOutException("out of board range.");
        }
        return result;
    }

    /**
     * Check way.
     * @param cells array of cells fo check
     * @return result of check
     */
    public boolean checkWay(Cell[] cells) {
        boolean result = true;
        for (Cell cell : cells) {
            if (cell.getFigure() instanceof Figure) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * Move figure.
     * @param source start cell
     * @param dest destination cell
     * @return result of move
     * @throws OccupiedWayException Figure can't move, because on the way have another figures
     * @throws FigureNotFoundException Figure not found
     */
    public boolean move(Cell source, Cell dest) throws OccupiedWayException, FigureNotFoundException {
        boolean result = false;
        if (source.getFigure() instanceof  Figure) {
            if (this.checkWay(source.getFigure().way(dest))) {
                source.getFigure().clone(dest);
                result = true;
            } else {
                throw new OccupiedWayException("Figure can't move, because on the way have another figures");
            }
        } else {
            throw new FigureNotFoundException("Figure not found");
        }
        return result;
    }

    /**
     * Getter.
     * @return array of figures
     */
    public Figure[] getFigures() {
        return figures;
    }
    /**
     * Getter.
     * @return array of cells
     */
    public Cell[][] getBoard() {
        return board;
    }
}