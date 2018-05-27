package ru.job4j.chess;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class Pawn extends Figure {
    private boolean directionNorth;

    Pawn(int x, int y, boolean directionNorth) {
        super(x, y);
        this.directionNorth = directionNorth;
    }

    @Override
    Cell[] way(Cell source, Cell dest) throws ImposibleMoveException {
        if (source.getX() != dest.getX()) {
            throw new ImposibleMoveException("This figure does not move this...");
        }
        int sizeWay;
        boolean ferstStep;
        if (source.getY() == 1 || source.getY() == 6) {
            sizeWay = 2;
            ferstStep = true;
        } else {
            sizeWay = 1;
            ferstStep = false;
        }
        Cell[] arrWay = new Cell[sizeWay];
        int index = 0;
        int wayX = source.getX();
        int wayY = source.getY();
        int increment = 1;
        if (directionNorth) {
            increment = -1;
        }
        do {
            wayY += increment;
            if ((!ferstStep && index > 0) || (ferstStep && index > 1)) {
                throw new ImposibleMoveException("Not correct way...");
            }
            arrWay[index++] = new Cell(wayX, wayY);
        } while (!dest.equals(new Cell(wayX, wayY)));
        return arrWay;
    }

    @Override
    Figure copy(Cell dest) {
        return new Pawn(dest.getX(), dest.getY(), this.directionNorth);
    }
}
