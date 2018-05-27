package ru.job4j.chess;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class Bishop extends Figure {

    Bishop(int x, int y) {
        super(x, y);
    }

    Bishop(Cell cell) {
        super(cell);
    }

    @Override
    public Cell[] way(Cell source, Cell dest) throws ImposibleMoveException {
        if ((source.getX() + source.getY()) % 2 != (dest.getX() + dest.getY()) % 2
                || source.getX() == dest.getX()
                || source.getY() == dest.getY()) {
            throw new ImposibleMoveException("This figure does not move this...");
        }
        Cell[] arrWay = new Cell[7];
        int index = 0;
        int wayX = source.getX();
        int wayY = source.getY();
        int stepX = Integer.compare(dest.getX(), wayX);
        int stepY = Integer.compare(dest.getY(), wayY);
        while (dest.getX() != wayX || dest.getY() != wayY) {
            wayX += stepX;
            wayY += stepY;
            arrWay[index++] = new Cell(wayX, wayY);
            if (index > 6) {
                break;
            }
        }
        if (!dest.equals(new Cell(wayX, wayY))) {
            throw new ImposibleMoveException("Not correct move...");
        }
        if (wayX < 0 || wayX > 7 || wayY < 0 || wayY > 7) {
            throw new ImposibleMoveException("Move out of board...");
        }
        return arrWay;
    }

    @Override
    public Figure copy(Cell dest) {
        return new Bishop(dest);
    }
}