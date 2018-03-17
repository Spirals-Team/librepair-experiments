package ru.job4j.testtask;

import ru.job4j.testtask.exceptions.ImpossibleMoveException;


/**
 * Класс описывает модель фигуры.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 09.09.17;
 * @version $Id$
 * @since 0.1
 */
public abstract class Figure {
    /**
     * позиция.
     */
    private final Cell pozition;
    /**
     * имя или цвет.
     */
    private String name;

    /**
     * Конструктор.
     * @param pozition позиция фигуры.
     * @param name     название фигуры.
     */
    public Figure(String name, Cell pozition) {
        this.pozition = pozition;
        this.name = name;
    }

    /**
     * Метод описывает движение фигуры.
     * @param dist точка куда хотели бы пойти.
     * @return массив ячеек с ходами.
     * @throws ImpossibleMoveException невозможность хода.
     */
    public abstract Cell[] way(Cell dist) throws ImpossibleMoveException;

    /**
     * метод костыль на геттер.
     * @param cell клетка кот нужно проверить.
     * @return тут или не тут стоит наша фигура.
     */
    public boolean isOneCell(Cell cell) {
        return  cell.equals(pozition);
    }

    /**
     * Метод задает новое положение фигуры.
     * @param cell клетка.
     */
    public void clone(Cell cell) {
        this.pozition.setHeight(cell.getHeight());
        this.pozition.setWidth(cell.getWidth());
    }
    public void clonE(Cell cell) {
        this.pozition.moveToNewCell(cell);
    }

    @Override
    public String toString() {
        return "Figure{"
                + "pozition="
                + pozition
                + ", name='"
                + name
                + '\''
                + '}';
    }

    /**
     * Equals.
     * @param o object.
     * @return true or false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass()  != o.getClass()) {
            return false;
        }

        Figure figure = (Figure) o;

        if (pozition != null ? !pozition.equals(figure.pozition) : figure.pozition != null) {
            return false;
        }
        return name != null ? name.equals(figure.name) : figure.name == null;
    }

    /**
     * hashCode.
     * @return cod.
     */
    @Override
    public int hashCode() {
        int result = pozition != null ? pozition.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
