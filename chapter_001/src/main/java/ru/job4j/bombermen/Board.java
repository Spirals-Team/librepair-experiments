package ru.job4j.bombermen;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Класс реализует функционал игровой доски
 */
public class Board {
    // игровая доска
    private final ReentrantLock[][] board;
    // размер доски
    private final Cell sizeXY;
    private final int countWall;
    private final ReentrantLock moveLock = new ReentrantLock();

    /**
     * Конструктор с начальной инициализацией доски
     * @param sizeX размер по оси X
     * @param sizeY размер по оси Y
     */
    Board (final int sizeX, final int sizeY, final int countWall) {
        board = new ReentrantLock[sizeX][sizeY];
        sizeXY = new Cell( sizeX,sizeY);
        this.countWall = countWall;
    }

    public void initBoard(final Hero hero) {
        int x;
        int y;
        int k = countWall;
        Random rn = new Random();

        moveLock.lock();

        for(x = 0; x < sizeXY.getX(); x++) {
            for (y = 0; y < sizeXY.getY(); y++){
                board[x][y] = new ReentrantLock();
            }
        }

        while (k  > 0) {
            x = rn.nextInt(sizeXY.getX());
            y = rn.nextInt(sizeXY.getY());
            if (board[x][y].isLocked()) {
                continue;
            }
            board[x][y].lock();
            k--;
        }

        do {
            x = rn.nextInt(sizeXY.getX());
            y = rn.nextInt(sizeXY.getY());
        } while (board[x][y].isLocked());
        hero.setCurXY(new Cell(x, y));

        System.out.println(this);
        moveLock.unlock();
    }

    /**
     * Метод реализует перемещение фигуры из начальной точки а конечную
     * @param source начальная точка
     * @param dest конечная точка
     * @return успех перемещения
     * @throws InterruptedException прокидываем наверх прерывание для краткости кода
     */
    public boolean move(final Cell source, final Cell dest) {
        boolean result = false;
        moveLock.lock();
        if (!board[source.getX()][source.getY()].isLocked()) {
            board[source.getX()][source.getY()].lock();
        }
        System.out.println("Moving...");
        if (checkXY(dest)) {
            try {
                result = board[dest.getX()][dest.getY()].tryLock(500, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (result) {
                board[source.getX()][source.getY()].unlock();
                System.out.println(String.format("Move from %s to %s", source, dest));
            }
        } else {
            System.out.println("No move");
        }
        moveLock.unlock();
        return result;
    }

    public Cell getSizeXY() {
        return sizeXY;
    }

    private boolean checkXY(final Cell cell) {
        return cell.getX() < getSizeXY().getX() && cell.getX() >= 0 && cell.getY() < getSizeXY().getY()
                && cell.getY() >= 0;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < sizeXY.getX(); i++) {
            for (int j = 0; j < sizeXY.getY(); j++) {
                s.append( board[j][i].isLocked() ? "X" : "0");
            }
            s.append("\n");
        }
        return s.toString();
    }
}
