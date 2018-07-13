package ru.job4j.bombermen;

/**
 * Абстрактный класс Фигура - основа для всех фигур в игре
 */
public class Figure {
    // текущие координаты фигуры - не финальные так как могут меняться во время игры
    private Cell curXY;
    // тип фигуры
    private final Type type ;

    /**
     * Конструктор с параметрами
     * @param type тип фигуры
     * @param cell
     */
    Figure (final Type type, final Cell cell) {
        this.type = type;
        this.curXY = cell;
    }

    public Cell getCurXY() {
        return curXY;
    }

    public void setCurXY(final Cell cell) {
        curXY = cell;
    }

    @Override
    public String toString() {
        return curXY.toString();
    }
}
