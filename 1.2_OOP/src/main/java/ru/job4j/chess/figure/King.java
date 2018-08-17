package ru.job4j.chess.figure;

import ru.job4j.chess.ImpossibleMoveException;
import ru.job4j.chess.Cell;
/**
 * Figure King.
 */
public class King extends Figure {
    /**
     * Constructor.
     * @param position our postion on the board
     * @param color our color
     */
    public King(Cell position, String color) {
        super(position, color);
    }
    /**
     * Way.
     * @param dist our target cell
     * @return array of cells on the way
     * @throws ImpossibleMoveException King can't move
     */
    public Cell[] way(Cell dist) throws ImpossibleMoveException {
        int x = this.position.getX() - dist.getX();
        int y = this.position.getY() - dist.getY();
        Cell[] cells = new Cell[1];
        if (Math.abs(x) == Math.abs(y) && Math.abs(x) == 1) {
            cells[0] = dist;
        } else if (x == 0 && Math.abs(y) == 1) {
            cells[0] = dist;
        } else if (y == 0 && Math.abs(x) == 1) {
            cells[0] = dist;
        } else {
            throw new ImpossibleMoveException("King can't move to this destination cell");
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
                temp[i] = new King(dist, this.color);
            }
        }
        this.position.setFigure(null);
    }
}


