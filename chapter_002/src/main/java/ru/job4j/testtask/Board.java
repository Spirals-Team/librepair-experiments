package ru.job4j.testtask;

import ru.job4j.testtask.exceptions.FigureNotFoundException;
import ru.job4j.testtask.exceptions.ImpossibleMoveException;
import ru.job4j.testtask.exceptions.OccupiedWayException;

import java.util.Arrays;

/**
 * Доска.
 * @author Hincu Andrei (andreih1981@gmail.com) by 09.09.17;
 * @version $Id$
 * @since 0.1
 */
public class Board {
    /**
     * массив фигур.
     */
    private Figure[] figures;
    /**
     * позиция в массиве.
     */
    private int pozition = 0;

    /**
     * конструктор доски.
     * @param figures массив.
     */
    public Board(Figure[] figures) {
        this.figures = figures;
    }

    /**
     * Метод возвращает массив существующих фигур.
     * @return figure.
     */
    public Figure[] getFigures() {
        return Arrays.copyOf(figures, pozition);
    }

    /**
     * добавить новую фигуру в массив.
     * @param figure фигура.
     */
    public void putFigure(Figure figure) {
        this.figures[pozition++] = figure;
    }

    /**
     * движение фигуры.
     * @param source начальное положение.
     * @param dist пункт назначения.
     * @return новое положение фигуры.
     * @throws ImpossibleMoveException невозможно так ходить.
     * @throws OccupiedWayException мессто занято.
     * @throws FigureNotFoundException в этой клетке нет фигуры.
     */
    boolean move(Cell source, Cell dist) throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {
        Figure figure = null;
        boolean end = false;
        for (Figure figure1 : figures) {
            if (figure1 == null) {
                break;
            }
            if (figure1.isOneCell(source)) {
                figure = figure1;
            }
        }
        if (figure == null) {
            throw new FigureNotFoundException("Figure not Found!");
        }
        Cell[]way = figure.way(dist);
        if (way.length == 0) {
            throw new ImpossibleMoveException("Impossible move.");
        }
        for (Figure figure1 : figures) {
            if (figure1 == null) {
                break;
            }
            for (Cell cell : way) {
                if (figure1.isOneCell(cell)) {
                    throw new OccupiedWayException("Occupiend Way!");
                }
            }
        }
        figure.clone(dist);
        end = true;
        return end;
    }
}
