package ru.job4j.chess.board;

import ru.job4j.chess.exeption.*;
import ru.job4j.chess.figure.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class Board {
    private int position = 0;
    private Figure[] figures = new Figure[32];
    public void add(Figure figure) throws ImpossibleMoveException, OccupiedWayException {
        for (int i = 0; i < position; i++) {
            if (figures[i].exist(figure)) {
                throw new OccupiedWayException("Ячейка занята другой фигурой");
            }
        }
        figures[position++] = figure;
    }
    public boolean move(Cell source, Cell dest) throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {
        Figure curFigure = null;
        int cur;
        for (cur = 0; cur < position; cur++) {
            if (figures[cur].exist(source)) {
                curFigure = figures[cur];
                break;
            }
        }
        if (curFigure == null) {
            throw new FigureNotFoundException("В заданной ячейки нет фигуры");
        }
        Cell[] way = curFigure.way(source, dest);
        for (int i = 0; i < position; i++) {
            for (int j = 0; j < way.length; j++) {
                if (figures[i].exist(way[j])) {
                    throw new OccupiedWayException("Ячейка занята другой фигурой");
                }
            }
        }
        figures[cur] = curFigure.copy(dest);
        return true;
    }
}
