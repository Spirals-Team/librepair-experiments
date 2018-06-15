package ru.job4j.chess;

/**
 * Класс шахматной доски.
 * @author Daniil Emelyanov
 * @since 17.04.2018
 * @version 0.1
 */
public class Board {
    /**
     * Массив фигур доски
     */
    Figure[] figures = new Figure[32];
    /**
     * Счетчик добавления фигуры
     */
    private int count = 0;
    /**
     * Метод реализующий движение фигуры по доске.
     *
     * @param source начальная клетка.
     * @param dest   клетка куда нужно пойти.
     * @return вернет true если ход произошел, или false если нет.
     * @throws ImposableMoveException  ошибка попытки совершить ход противоречиющий правилам.
     * @throws OccupiedWayException    ошибка попытки движения фигуры, если на ее пути есть другие фигуры.
     * @throws FigureNotFoundException ошибка отсутсвия фигуры в начальной ячейке.
     */
    boolean move(Cell source, Cell dest) throws ImposableMoveException,
            OccupiedWayException,
            FigureNotFoundException {
        for (int index = 0; index < figures.length; index++) {
            if (figures[index] != null && figures[index].position.equals(source)) {
            Cell[] route = figures[index].way(source, dest);
                for (Cell busy: route) {
                    if (figures[index] != null && figures[index].position.equals(busy)) {
                        throw  new OccupiedWayException("the route is busy");
                    }
                }
            } else {
                throw new FigureNotFoundException("404 Figure not found");
            }
         figures[index] = figures[index].copy(dest);
            return true;
        }
        return false;
    }

    /**
     * Метод добавления новой фигуры.
     * @param figure новая вигура
     */
    public void  add(Figure figure) {
        figures[count++] = figure;
    }
}