package ru.job4j.chess;

import java.util.Arrays;

/**
 * Класс, определяющий фигуру Слон
 * @author Alex Rumyantcev
 * @version $Id$
 */
public class Bishop extends Figure {

    /**
     * Конструктор Слона
     * @param cell ячейка куда ставить слона
     * @param board доска, на которую ставить слона
     */
    public Bishop(final Cell cell, final Board board) {
        super(cell);
        board.setFigure(this, cell);
    }

    /**
     * Просчет маршрута перемещения
     * @param source начальная клетка
     * @param dest  финальная клетка
     * @return массив из клето, которые надо пройти
     * @throws ImposibleMoveException путь для данной фигуры невозможен
     */
    public Cell[] way(final Cell source, final Cell dest) throws ImposibleMoveException {
        Cell[] way = new Cell[7];
        int position = 0;
        final int dx = dest.getX() - source.getX();
        final int dy = dest.getY() - source.getY();
        final int signx = dx < 0 ? -1 : 1;
        final int signy = dy < 0 ? -1 : 1;
        if (Math.abs(dx) != Math.abs(dy) || dx == 0) {
            throw new ImposibleMoveException();
        } else {
            for (int i = 0; i < Math.abs(dx); i++) {
                way[position++] = new Cell(source.getX() + signx * (i + 1), source.getY() + signy * (i + 1));
            }
        }
        return Arrays.copyOf(way, position);
    }

    /**
     * Копирование слона в требуемую клетку
     * @param dest куда копировать
     * @param board на какуб доску копировать
     * @return ссылка на новую фигуру
     */
    public Figure copy(final Cell dest, final Board board) {
        return new Bishop(dest, board);
    }

    /**
     *  Перемещение фигуры
     * @param source исходная клетка
     * @param dest финальная клетка
     * @param board доска, на которой происходит перемещение
     * @return получилось перемещение или нет
     * @throws ImposibleMoveException путь для данной фигуры невозможен
     * @throws OccupiedWayException путь занят другой фигурой
     * @throws FigureNotFoundException нет фигур в исходной позиции
     */
    public boolean move(final Cell source, final Cell dest, final Board board)
            throws ImposibleMoveException, OccupiedWayException, FigureNotFoundException {
        if (board.showFigure(source) == null) {
            throw new FigureNotFoundException();
        }
        for (Cell cell:this.way(source, dest)) {
            if (board.showFigure(cell) !=  null) {
                throw new OccupiedWayException();
            }
        }
        board.delFigure(source);
        this.copy(dest, board);
        return true;
    }

}
