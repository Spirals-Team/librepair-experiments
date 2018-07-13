package ru.job4j.bombermen;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * Класс реализует игру Bombermen
 */
public class Bombermen implements Runnable{
    private final int sizeX;
    private final int sizeY;
    private final Board board;
    // буфер обмена направлением движения между потоками
    private final SynchronousQueue<Direction> dir = new SynchronousQueue<>();
    // буфер для потоков
    private final ExecutorService pool = Executors.newCachedThreadPool();
    private final Hero hero;

    Bombermen(final int sizeX, final int sizeY, final int countWall) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.board = new Board(sizeX, sizeY, countWall);
        this.hero = new Hero( 0, 0, this.board, dir);
    }

    /**
     * Метод проводит подготовительные действия игры
     * расставляет стены, монстров  и героя
     */
    public void init() {
        board.initBoard(hero);
    }

    /**
     * Поток для игры
     */
    public void run() {
        pool.submit(hero);
        System.out.println(String.format("Stand HERO in %s", hero));
        // 10 раз двигаем героя
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println(String.format("Step # %s", i+1));
                dir.put(getDirect());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
        // останавливаем все потоки
        pool.shutdownNow();
        try {
            pool.awaitTermination(5000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Bombermen process ended");
    }

    /**
     * Метод выдает случайное направление движения
     * @return направление
     */
    private Direction getDirect() {
        Random rn = new Random();
        return Direction.values()[rn.nextInt(4)+1];
    }
}
