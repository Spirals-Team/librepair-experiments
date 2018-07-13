package ru.job4j.chess;

/**
 * Класс, определяющий игровую доску
 * @author Alex Rumyantcev
 * @version $Id$
 */
public class Board {

    // массив ссылок на фигуры на игровой доске. null - фигуры в этой клетке нет.
    private Figure[][] figures = new Figure[8][8];

    public Board(final Figure[][] board) {
        this.figures = board;
    }

    /**
     * Установить фигуру на клетку
     * @param fig фигура, которую надо установить
     * @param cell клетка для установки
     */
    public void setFigure(final Figure fig, final Cell cell) {
        this.figures[cell.getX()][cell.getY()] = fig;
    }

    /**
     * Удалить фигуру с клетки
     * @param cell целевая клетка
     */
    public void delFigure(final Cell cell) {
        this.figures[cell.getX()][cell.getY()] = null;
    }

    /**
     * Показать фигуру на клетке
     * @param cell целевая клетка
     * @return фигура, что стоит на клетке
     */
    public Figure showFigure(final Cell cell) {
        return this.figures[cell.getX()][cell.getY()];
    }
}
