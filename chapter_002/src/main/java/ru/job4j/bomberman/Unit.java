package ru.job4j.bomberman;

import java.util.logging.Logger;

import static java.lang.String.format;
import static java.lang.Thread.currentThread;

/**
 * Unit.
 */
public abstract class Unit implements Runnable {

    private final static Logger LOG = Logger.getLogger(Unit.class.getName());

    private Point pos;

    protected abstract void move();

    protected abstract void initStartPosition();

    @Override
    public void run() {
        initStartPosition();
        while (!currentThread().isInterrupted()) {
            move();
        }
    }

    protected Point nextPos(final Direction direction) {
        Point newPoint = new Point(getPos());
        switch (direction) {
            case LEFT:
                newPoint.setPosX(newPoint.getPosX() - 1);
                break;
            case UP:
                newPoint.setPosY(newPoint.getPosY() + 1);
                break;
            case RIGHT:
                newPoint.setPosX(newPoint.getPosX() + 1);
                break;
            case DOWN:
                newPoint.setPosY(newPoint.getPosY() - 1);
                break;
            default:
                throw new IllegalArgumentException(
                    format("Direction with title { %s } not found.", direction));
        }
        return newPoint;
    }

    /**
     * Get unit position
     *
     * @return unit position
     */
    public Point getPos() {
        return pos;
    }

    /**
     * Set unit position
     *
     * @param pos position
     */
    public void setPos(final Point pos) {
        this.pos = pos;
    }
}
