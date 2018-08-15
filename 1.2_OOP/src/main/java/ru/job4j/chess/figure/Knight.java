package ru.job4j.chess.figure;

import ru.job4j.chess.ImpossibleMoveException;
import ru.job4j.chess.Cell;
/**
 * Figure Knight.
 */
public class Knight extends Figure {
    /**
     * Constructor.
     * @param position our postion on the board
     * @param color our color
     */
    public Knight(Cell position, String color) {
        super(position, color);
    }
    /**
     * Way.
     * @param dist our target cell
     * @return array of cells on the way
     * @throws ImpossibleMoveException Knight can't move
     */
    public Cell[] way(Cell dist) throws ImpossibleMoveException {
        int x = this.position.getX() - dist.getX();
        int y = this.position.getY() - dist.getY();
        Cell[] cells = new Cell[1];
        if ((Math.abs(x) == 2 && Math.abs(y) == 1) || (Math.abs(x) == 1 && Math.abs(y) == 2)) {
            cells[0] = dist;
        } else {
            throw new ImpossibleMoveException("Knight can't move to this destination cell");
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
                temp[i] = new Knight(dist, this.color);
            }
        }
        this.position.setFigure(null);
    }
}
