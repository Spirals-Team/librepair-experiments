package ru.job4j.bomberman;

import java.util.logging.Logger;

import static java.lang.Thread.currentThread;
import static ru.job4j.bomberman.GeneratorUtils.randomPoint;

/**
 * Hero.
 */
public class Hero extends Unit {
    private final static Logger LOG = Logger.getLogger(Hero.class.getName());

    private Board board;
    private Direction direction;

    public Hero(final Board board) {
        this.board = board;
        this.direction = Direction.getRandomDirection();
    }

    protected void initStartPosition() {
        int size = board.getGameBoard().length;
        boolean isStartSet = false;
        Point startPos;
        while (!isStartSet) {
            startPos = randomPoint(0, size);
            isStartSet = board.setIfPossible(startPos);
            setPos(startPos);
        }
        LOG.info(String.format("%s: Hero init on [%d][%d]",
            currentThread().getName(),
            getPos().getPosX(), getPos().getPosY()));
    }

    protected void move() {
        boolean isMoved = false;
        while (!isMoved) {
            Point newPos = nextPos(getDirection());
            LOG.info(String.format("%s: Hero tries lock [%d][%d]",
                currentThread().getName(),
                getPos().getPosX(), getPos().getPosY()));
            if (board.setIfPossible(newPos)) {
                board.unlockPosition(getPos());
                setPos(newPos);
                isMoved = true;
                LOG.info(String.format("%s: Hero moves on [%d][%d]",
                    currentThread().getName(),
                    getPos().getPosX(), getPos().getPosY()));
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Get direction
     *
     * @return direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Set direction
     *
     * @param direction direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
