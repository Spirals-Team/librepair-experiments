package ru.job4j.chess.figure;

import ru.job4j.chess.ImpossibleMoveException;
import ru.job4j.chess.Cell;
/**
 * Figure Castle.
 */
public class Castle extends Figure {
    /**
     * Constructor.
     * @param position our postion on the board
     * @param color our color
     */
    public Castle(Cell position, String color) {
        super(position, color);
    }
    /**
     * Way.
     * @param dist our target cell
     * @return array of cells on the way
     * @throws ImpossibleMoveException Catle can't move
     */
    public Cell[] way(Cell dist) throws ImpossibleMoveException {
        int x = this.position.getX() - dist.getX();
        int y = this.position.getY() - dist.getY();
        Cell[] cells;
        if (x == 0) {
            int index = Math.abs(y);
            cells = new Cell[index];
            int xStep = 0;
            int yStep = y / Math.abs(y);
            for (int i = 0; i < index; i++) {
                cells[i] = dist.getBoard().findCellByXY((dist.getX()), (char) (dist.getY() + yStep * i));
            }
        } else if (y == 0) {
            int index = Math.abs(x);
            cells = new Cell[index];
            int xStep = x / Math.abs(x);
            int yStep = 0;
            for (int i = 0; i < index; i++) {
                cells[i] = dist.getBoard().findCellByXY((char) (dist.getX() + xStep * i), (dist.getY()));
            }
        } else {
            throw new ImpossibleMoveException("Castle can't move to this destination cell");
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
                temp[i] = new Castle(dist, this.color);
            }
        }
        this.position.setFigure(null);
    }
}