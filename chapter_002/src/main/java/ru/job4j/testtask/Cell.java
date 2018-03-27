package ru.job4j.testtask;

/**
 * Ячейки шахматной доски.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 09.09.17;
 * @version $Id$
 * @since 0.1
 */
public class Cell {
    /**
     * положение ячейки по ширине доски.
     */
    private int width;

    /**
     * положение ячейки по высоте доски.

     */
    private int height;

    /**
     * геттер.
     * @return ширина.
     */
    public int getWidth() {
        return width;
    }

    /**
     * сеттер.
     * @param width ширина.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * геттер.
     * @return высота.
     */
    public int getHeight() {
        return height;
    }

    /**
     * сеттер.
     * @param height высота.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * конструктор.
     * @param width ширина.
     * @param height высота.
     */
    public Cell(int width, int height) {

        this.width = width;
        this.height = height;
    }

    /**
     * соответствует ли.
     * @param o o.
     * @return да или нет.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Cell cell = (Cell) o;

        if (width != cell.width) {
            return false;
        }
        return height == cell.height;
    }

    /**
     * хэшкод.
     * @return хэшкод.
     */
    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        return result;
    }

    /**
     * тостринг.
     * @return стринг.
     */
    @Override
    public String toString() {
        return "Cell{"
                + "width="
                + width
                + ", height="
                + height
                + '}';
    }
    public void moveToNewCell(Cell dist) {
        this.height = dist.height;
        this.width = dist.width;
    }
}