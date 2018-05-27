package ru.job4j.chess;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class Queen extends Figure {
    Queen(int x, int y) {
        super(x, y);
    }

    @Override
    Cell[] way(Cell source, Cell dest) throws ImposibleMoveException {
        if ((source.getX() == dest.getX() && source.getY() != dest.getY())
                || (source.getX() != dest.getX() && source.getY() == dest.getY())
                || (source.getX() + source.getY()) % 2 == (dest.getX() + dest.getY()) % 2) {
            Cell[] arrWay = new Cell[7];
            int index = 0;
            int incrementX = 1;
            int incrementY = 1;
            int wayX = source.getX();
            int wayY = source.getY();
            if (dest.getY() < source.getY()) {
                incrementY = -1;
                if (dest.getX() < source.getX()) {
                    incrementX = -1;
                } else if (dest.getX() == source.getX()) {
                    incrementX = 0;
                }
            } else if (dest.getX() < source.getX()) {
                incrementX = -1;
            } else if (dest.getX() == source.getX()) {
                incrementX = 0;
            }
            if (dest.getY() == source.getY()) {
                incrementY = 0;
            }
            do {
                wayX += incrementX;
                wayY += incrementY;
                if (wayX < 0 || wayX > 7 || wayY < 0 || wayY > 7) {
                    throw new ImposibleMoveException("Not correct move...");
                }
                arrWay[index++] = new Cell(wayX, wayY);
            } while (!dest.equals(new Cell(wayX, wayY)));
            return arrWay;
        } else {
            throw new ImposibleMoveException("This figure does not move this...");
        }
    }

    @Override
    Figure copy(Cell dest) {
        return new Queen(dest.getX(), dest.getY());
    }
}
