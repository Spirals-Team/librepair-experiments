package ru.job4j.chess;
/**
 * Cell.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class Cell {
    /*** Horizontal coordinate.*/
    private int x;
    /*** Vertical coordinate.*/
    private int y;
    /**
     * Constructor.
     * @param x - horizontal coordinate
     * @param y - vertical coordinate
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Cell cell = (Cell) obj;
        return this.x == cell.x && this.y == cell.y;
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    /**
     * Get x.
     * @return - x
     */
    public int getX() {
        return this.x;
    }
    /**
     * Get y.
     * @return - y
     */
    public int getY() {
        return this.y;
    }
}
