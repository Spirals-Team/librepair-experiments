package ru.job4j.chess;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class Board {
    Figure[][] figures = new Figure[8][8];

    public boolean move(Cell source, Cell dest) throws ImposibleMoveException,
            OccupiedWayException, FigureNotFoundException {
        if (source == null) {
            throw new FigureNotFoundException("Figure not found...");
        }
        Cell tempCell = new Cell(source.getX(), source.getY());
        if (figures[tempCell.getX()][tempCell.getY()] != null) {
            Cell[] arrWay = figures[tempCell.getX()][tempCell.getY()].way(tempCell, dest);
            for (int i = 0; i < arrWay.length; i++) {
                if (arrWay[i] != null) {
                    if (figures[arrWay[i].getX()][arrWay[i].getY()] != null) {
                        throw new OccupiedWayException("On the way there is a figure...");
                    }
                }
            }
            figures[dest.getX()][dest.getY()] = figures[source.getX()][source.getY()].copy(dest);
            figures[source.getX()][source.getY()] = null;
            return true;
        } else {
            throw new FigureNotFoundException("Figure not found...");
        }
    }
}
