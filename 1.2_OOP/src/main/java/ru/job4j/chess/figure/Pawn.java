package ru.job4j.chess.figure;

import ru.job4j.chess.ImpossibleMoveException;
import ru.job4j.chess.Cell;
/**
 * Figure Pawn.
 */
public class Pawn extends Figure {
    /**
     * Constructor.
     * @param position our postion on the board
     * @param color our color
     */
    public Pawn(Cell position, String color) {
        super(position, color);
    }
    /**
     * Way.
     * @param dist our target cell
     * @return array of cells on the way
     * @throws ImpossibleMoveException Pawn can't move
     */
    public Cell[] way(Cell dist) throws ImpossibleMoveException {
        int x = this.position.getX() - dist.getX();
        int y = this.position.getY() - dist.getY();
        int index = Math.abs(y);
        Cell[] cells;
        if (((this.color.equals("white") && this.position.getY() == '2') || (this.color.equals("black") && this.position.getY() == '7')) && Math.abs(y) == 2 && x == 0) {
                cells = new Cell[index];
            int yStep = y / Math.abs(y);
            for (int i = 0; i < index; i++) {
                cells[i] = dist.getBoard().findCellByXY((dist.getX()), (char) (dist.getY() + yStep * i));
            }
        } else if (this.color.equals("white") && x == 0 && Math.abs(y) == 1 && this.position.getY() + Math.abs(y) == dist.getY()) {
            cells = new Cell[1];
            cells[0] = dist;
        } else if (this.color.equals("black") && x == 0 && Math.abs(y) == 1 && this.position.getY() - Math.abs(y) == dist.getY()) {
            cells = new Cell[1];
            cells[0] = dist;
        } else {
            throw new ImpossibleMoveException("Pawn can't move to this destination cell");
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
                temp[i] = new Pawn(dist, this.color);
            }
        }
        this.position.setFigure(null);
    }
}
