package ru.job4j.controltask.bomberman.entities;
import ru.job4j.controltask.bomberman.board.Board;
import ru.job4j.controltask.bomberman.board.Cell;
import ru.job4j.controltask.bomberman.move.Movements;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * Class Bomberman.
 */
public class BomberMan extends Entity implements Movements {
    /**
     * @param START_X start position by X.
     */
    private static final int START_X = 0;
    /**
     * @param START_Y start position by Y.
     */
    private static final int START_Y = 0;


    /**
     * Constructor.
     * The position for the starting position of the Bomberman is selected randomly.
     * @param board our game board
     */
    public BomberMan(Board board) {
        super(board);
    }

    /**
     * Method for init entity on the board.
     */
    @Override
    void initEntity() {
        this.currentCell = this.board.getBoard()[START_X][START_Y];
    }

    /**
     * Method for Thread.
     */
    @Override
    public void run() {
        currentCell.lock();
        while (!board.isFinish()) {
            int temp = new Random().nextInt(4);
            switch (temp) {
                case 0:
                    moveUp();
                    break;
                case 1:
                    moveRight();
                    break;
                case 2:
                    moveDown();
                    break;
                case 3:
                    moveLeft();
                    break;
                default:
                    break;
            }
        }
        System.out.println("Игра окончена");
    }

    /**
     * Method for move up.
     */
    @Override
    public void moveUp() {
        int[] steps = this.step.getStepUp();
        this.checkStep(steps);
    }

    /**
     * Method for move right.
     */
    @Override
    public void moveRight() {
        int[] steps = this.step.getStepRight();
        this.checkStep(steps);
    }

    /**
     * Method for move down.
     */
    @Override
    public void moveDown() {
        int[] steps = this.step.getStepDown();
        this.checkStep(steps);
    }

    /**
     * Method for move left.
     */
    @Override
    public void moveLeft() {
        int[] steps = this.step.getStepLeft();
        this.checkStep(steps);
    }

    /**
     * Method for check step
     * @param steps steps
     */
    private void checkStep(int[] steps) {
        int moveX = currentCell.getX() + steps[0];
        int moveY = currentCell.getY() + steps[1];
        if (moveX < board.getMaxX() && moveX >= board.getMinX() && moveY < board.getMaxY() && moveY >= board.getMinY()) {
            Cell newCell = (Cell) board.getBoard()[moveX][moveY];
            try {
                if (newCell.tryLock(500, TimeUnit.MILLISECONDS)) {
                    currentCell.unlock();
                    currentCell = newCell;
                    if (currentCell.equals(this.board.getFinish())) {
                        this.board.setFinish(true);
                        System.out.println(Thread.currentThread().getName() + " добрался до финиша. Клетка: " + currentCell.getX() + " " + currentCell.getY());
                    }
                } else {
                    System.out.println("Клетка занята. Доступ запрещен.");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Клетка находится за границей игрового поля. Доступ запрещен.");
        }
    }
}
