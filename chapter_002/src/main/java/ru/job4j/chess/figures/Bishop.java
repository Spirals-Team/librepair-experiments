package ru.job4j.chess.figures;

import ru.job4j.chess.Board;
import ru.job4j.chess.Cell;
import ru.job4j.chess.Figure;
import ru.job4j.chess.exceptions.ImpossibleMoveException;
/**
 * Bishop.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class Bishop extends Figure {
    /**
     * Constructor.
     * @param position - position
     */
    public Bishop(Cell position) {
        super(position);
    }
    @Override
    public Cell[] way(Cell dist, Board board) throws ImpossibleMoveException {
        int xA = super.getPosition().getX();
        int yA = super.getPosition().getY();
        int xB = dist.getX();
        int yB = dist.getY();

        for (int i = 0; i < board.getPos(); i++) {
            if (board.getFigures()[i].getPosition().equals(dist)) {
                throw new ImpossibleMoveException("Impossible moving!!!");
            }
        }
        if (Math.abs(xB - xA) == Math.abs(yB - yA)) {
            Cell[] way = new Cell[Math.abs(xB - xA)];

            for (int i = 0; i < way.length; i++) {
                int dx = (xB > xA) ? 1 : -1;
                int dy = (yB > yA) ? 1 : -1;

                Cell cell = new Cell(xA + dx, yA + dy);
                way[i] = cell;

                xA = cell.getX();
                yA = cell.getY();
            }
            return way;
        } else {
            throw new ImpossibleMoveException("Impossible moving!!!");
        }
    }
}
