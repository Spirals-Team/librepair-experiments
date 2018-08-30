package ru.job4j.bomberman;

import java.util.logging.Logger;

import static java.lang.Thread.currentThread;
import static ru.job4j.bomberman.GeneratorUtils.getRandom;
import static ru.job4j.bomberman.GeneratorUtils.randomPoint;

/**
 * Monster.
 */
public class Monster extends Unit {

    private final static Logger LOG = Logger.getLogger(Monster.class.getName());

    private Board board;

    public Monster(final Board board) {
        this.board = board;
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
        LOG.info(String.format("%s: Monster init on [%d][%d]",
            currentThread().getName(),
            getPos().getPosX(), getPos().getPosY()));
    }

    protected void move() {
        boolean isMoved = false;
        while (!isMoved) {
            Point newPos = nextPos(Direction.getRandomDirection());
            LOG.info(String.format("%s: Monster tries lock [%d][%d]",
                currentThread().getName(),
                getPos().getPosX(), getPos().getPosY()));
            if (board.setIfPossible(newPos)) {
                board.unlockPosition(getPos());
                setPos(newPos);
                isMoved = true;
                LOG.info(String.format("%s: Monster moves on [%d][%d]",
                    currentThread().getName(),
                    getPos().getPosX(), getPos().getPosY()));
                try {
                    Thread.sleep(getRandom(500, 5000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
