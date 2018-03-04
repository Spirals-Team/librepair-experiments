package ru.job4j.chess;

import ru.job4j.chess.exceptions.FigureNotFoundException;
import ru.job4j.chess.exceptions.ImpossibleMoveException;
import ru.job4j.chess.exceptions.OccupiedWayException;
/**
 * Board.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class Board {
    /*** Array of figures.*/
    private Figure[] figures = new Figure[32];
    /*** Position of figure in array.*/
    private int pos = 0;
    /**
     * Get figures array.
     * @return figures
     */
    public Figure[] getFigures() {
        return this.figures;
    }
    /**
     * Get position of figure in array.
     * @return position
     */
    public int getPos() {
        return this.pos;
    }
    /**
     * Add figure in array.
     * @param figure - figure
     */
    public void addFigure(Figure figure) {
        this.figures[pos++] = figure;
    }
    /**
     * Move a figure to the cells.
     * @param source - source coordinate
     * @param dist - final coordinate
     * @return - true or false
     * @throws ImpossibleMoveException - if figure not move to coordinate
     * @throws OccupiedWayException - if way of occupied
     * @throws FigureNotFoundException - if figure not found
     */
    public boolean move(Cell source, Cell dist) throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {
        for (int i = 0; i < getPos(); i++) {
            if (!this.figures[i].getPosition().equals(source)) {
                throw new FigureNotFoundException("Figure not found!!!");
            }

            for (int j = 0; j < this.figures[i].way(dist, this).length; j++) {
                for (int k = 0; k < getPos(); k++) {
                    if (this.figures[i].way(dist, this)[j].equals(this.figures[k].getPosition())) {
                        throw new OccupiedWayException("Occupied way!!!");
                    }
                }
            }
        }
        return true;
    }
}
