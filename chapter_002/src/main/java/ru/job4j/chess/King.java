package ru.job4j.chess;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class King extends Figure {
    King(int x, int y) {
        super(x, y);
    }

    @Override
    Cell[] way(Cell source, Cell dest) throws ImposibleMoveException {
        if (dest.getX() - source.getX() > 1
                || dest.getX() - source.getX() < -1
                || dest.getY() - source.getY() > 1
                || dest.getY() - source.getY() < -1) {
            throw new ImposibleMoveException("This figure does not move this...");
        }
        Cell[] legalWay = new Cell[8];
        legalWay[0] = new Cell(source.getX() - 1, source.getY() - 1);
        legalWay[1] = new Cell(source.getX() - 1, source.getY() + 1);
        legalWay[2] = new Cell(source.getX() + 1, source.getY() - 1);
        legalWay[3] = new Cell(source.getX() + 1, source.getY() + 1);
        legalWay[4] = new Cell(source.getX() - 1, source.getY());
        legalWay[5] = new Cell(source.getX() + 1, source.getY());
        legalWay[6] = new Cell(source.getX(), source.getY() - 1);
        legalWay[7] = new Cell(source.getX(), source.getY() + 1);
        for (int i = 0; i < legalWay.length; i++) {
            if (legalWay[i].getX() < 0
                    || legalWay[i].getX() > 7
                    || legalWay[i].getY() < 0
                    || legalWay[i].getY() > 7) {
                legalWay[i] = null;
            }
            if (legalWay[i] != null && legalWay[i].equals(dest)) {
                Cell[] arrWay = new Cell[1];
                arrWay[0] = legalWay[i];
                return arrWay;
            }
        }
        throw new ImposibleMoveException("Move not for King");
    }

    @Override
    Figure copy(Cell dest) {
        return new King(dest.getX(), dest.getY());
    }
}
