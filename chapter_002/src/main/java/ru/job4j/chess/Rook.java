package ru.job4j.chess;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class Rook extends Figure {
    Rook(int x, int y) {
        super(x, y);
    }

    @Override
    Cell[] way(Cell source, Cell dest) throws ImposibleMoveException {
        if (source.getX() != dest.getX() && source.getY() != dest.getY()) {
            throw new ImposibleMoveException("This figure does not move this...");
        }
        Cell[] arrWay = new Cell[7];
        int index = 0;
        int increment = 1;
        int wayX = source.getX();
        int wayY = source.getY();
        if (dest.getX() == source.getX()) {
            if (dest.getY() < source.getY()) {
                increment = -1;
            }
            do {
                wayY += increment;
                if (wayY < 0 || wayY > 7) {
                    throw new ImposibleMoveException("Not the correct diagonal...");
                }
                arrWay[index++] = new Cell(wayX, wayY);
            } while (!dest.equals(new Cell(wayX, wayY)));
        } else {
            if (dest.getX() < source.getX()) {
                increment = -1;
            }
            do {
                wayX += increment;
                if (wayX < 0 || wayX > 7) {
                    throw new ImposibleMoveException("Not the correct diagonal...");
                }
                arrWay[index++] = new Cell(wayX, wayY);
            } while (!dest.equals(new Cell(wayX, wayY)));
        }
        return arrWay;
    }

    @Override
    Figure copy(Cell dest) {
        return new Rook(dest.getX(), dest.getY());
    }
}
