package ru.job4j.controltask.bomberman.entities;

import ru.job4j.controltask.bomberman.board.Board;
import ru.job4j.controltask.bomberman.board.Cell;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Class Monster.
 */
public class Monster extends Entity {
    /**
     * @param bomber Thread responsible for bomberman
     */
    private final Thread bomber;


    /**
     * Constructor.
     * The position for the starting position of the Bomberman is selected randomly.
     * @param board our game board
     * @param bomberman Thread responsible for bomberman
     */
    public Monster(Board board, Thread bomberman) {
        super(board);
        this.bomber = bomberman;

    }

    /**
     * Method for init entity on the board.
     */
    @Override
    void initEntity() {
        int x = (int) (board.getMaxX() * 0.2);
        int y = (int) (board.getMaxY() * 0.2);
        Cell temp;
        while (true) {
            temp = board.getBoard()[x + new Random().nextInt(board.getMaxX() - x)][y + new Random().nextInt(board.getMaxY() - y)];
            if (!temp.isLocked() && !temp.equals(board.getFinish())) {
                this.currentCell = temp;
                break;
            }
        }
    }

    /**
     * Method for Thread.
     */
    @Override
    public void run() {
        currentCell.lock();
        int[] steps = step.move();
        while (!board.isFinish()) {
            int moveX = currentCell.getX() + steps[0];
            int moveY = currentCell.getY() + steps[1];
            if (moveX < board.getMaxX() && moveX >= board.getMinX() && moveY < board.getMaxY() && moveY >= board.getMinY()) {
                Cell newCell = (Cell) board.getBoard()[moveX][moveY];
                if (newCell.isLocked()) {
                    Thread temp = newCell.getOwner();
                    if (temp.equals(bomber)) {
                        board.setFinish(true);
                        System.out.println(Thread.currentThread().getName() + " поймал " + bomber.getName() + ". Клетка: " + newCell.getX() + " " + newCell.getY());
                        break;
                    } else {
                        try {
                            if (newCell.tryLock(500, TimeUnit.MILLISECONDS)) {
                                currentCell.unlock();
                                currentCell = newCell;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    newCell.lock();
                    currentCell.unlock();
                    currentCell = newCell;
                }
            }
            steps = step.move();
        }
    }
}
