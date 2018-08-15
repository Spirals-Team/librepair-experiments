package ru.job4j.chess.figure;

import ru.job4j.chess.ImpossibleMoveException;
import ru.job4j.chess.Cell;

/**
 * Figure Bishop.
 */
public class Bishop extends Figure {
    /**
     * Constructor.
     * @param position our postion on the board
     * @param color our color
     */
    public Bishop(Cell position, String color) {
        super(position, color);
    }

    /**
     * Way.
     * @param dist our target cell
     * @return array of cells on the way
     * @throws ImpossibleMoveException Bishop can't move
     */
    public Cell[] way(Cell dist) throws ImpossibleMoveException {
        int x = this.position.getX() - dist.getX();
        int y = this.position.getY() - dist.getY();
        int index = Math.abs(x);
        Cell[] cells;
        if (Math.abs(x) == Math.abs(y)) {
            cells = new Cell[index];
            int xStep = x / Math.abs(x);
            int yStep = y / Math.abs(y);
            for (int i = 0; i < index; i++) {
                cells[i] = dist.getBoard().findCellByXY((char) (dist.getX() + xStep * i), (char) (dist.getY() + yStep * i));
            }
        } else {
            throw new ImpossibleMoveException("Bishop can't move to this destination cell");
        }
        return cells;
    }

    /**
     * Clone.
     * @param dist our target cell
     */
    public void clone(Cell dist) {
        Figure[] temp = this.position.getBoard().getFigures();
        for (int i = 0; i < temp.length; i++) {
            if (temp[i].equals(this.position.getFigure())) {
                temp[i] = new Bishop(dist, this.color);
            }
        }
        this.position.setFigure(null);
    }
}


