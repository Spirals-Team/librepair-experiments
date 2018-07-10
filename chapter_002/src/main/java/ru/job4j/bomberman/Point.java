package ru.job4j.bomberman;

/**
 * Point.
 */
public class Point {

    private int posX;
    private int posY;

    public Point(final int posX, final int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public Point(final Point point) {
        this.posX = point.getPosX();
        this.posY = point.getPosY();
    }

    /**
     * Get position on abscissa
     *
     * @return abscissa
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Set position on abscissa
     *
     * @param posX abscissa
     */
    public void setPosX(final int posX) {
        this.posX = posX;
    }

    /**
     * Get position on ordinate
     *
     * @return ordinate
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Get position on ordinate
     *
     * @param posY ordinate
     */
    public void setPosY(final int posY) {
        this.posY = posY;
    }
}
