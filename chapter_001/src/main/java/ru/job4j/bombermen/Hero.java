package ru.job4j.bombermen;

import java.util.concurrent.SynchronousQueue;

/**
 * Класс реализует героя в игре
 */
public class Hero implements Runnable {

    // буфер обмена направлением движения между потоками
    private final SynchronousQueue<Direction> dir;
    private final Figure figure;
    private final Board board;

    Hero (final int initX, final int initY, final Board board, final SynchronousQueue<Direction> dir) {
        figure = new Figure(Type.HERO, new Cell(initX, initY));
        this.board = board;
        this.dir = dir;
    }

    @Override
    /**
     * Метод реализует поток для героя
     * Управление движением происходит с помощью изменения параметра Direct класса Figure
     */
    public void run() {
        Direction d;
        this.board.move(figure.getCurXY(), figure.getCurXY());
        while (!Thread.currentThread().isInterrupted()) {
            try {
                d = dir.take();
                System.out.println(String.format("Direction: %s", d));
                if (this.board.move(this.figure.getCurXY(), cellDirect(this.figure.getCurXY(), d))) {
                    this.figure.setCurXY(cellDirect(this.figure.getCurXY(), d));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                continue;
            }
            System.out.println(String.format("Hero here %s", this.figure.getCurXY()));
        }
        System.out.println(String.format("%s ended", Thread.currentThread().getName()));
    }

    public void setCurXY(final Cell cell) {
        this.figure.setCurXY(cell);
    }

    /**
     * Метод реализует изменение ячейки при движении в определенном направлении и на определенной доске
     * @param cell исходное положение
     * @param direct направление
     * @return новое положение
     */
    private Cell cellDirect(final Cell cell, final Direction direct) {
        Cell result = cell;
        switch (direct) {
            case UP:    result = new Cell(cell.getX(), cell.getY()-1);
                break;
            case DOWN:  result = new Cell(cell.getX(), cell.getY()+1);
                break;
            case LEFT:  result = new Cell(cell.getX()-1, cell.getY());
                break;
            case RIGHT: result = new Cell(cell.getX()+1, cell.getY());
                break;
        }
        return result;
    }

    @Override
    public String toString() {
        return this.figure.toString();
    }
}
