package ru.job4j.multithreading.lift;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Класс лифт.
 * @author Hincu Andrei (andreih1981@gmail.com)on 04.01.2018.
 * @version $Id$.
 * @since 0.1.
 */
@ThreadSafe
public class Lift implements Runnable {
    private ArrayBlockingQueue<Integer> ext;
    private ArrayBlockingQueue<Integer> inside;
    private int level;
    private int heightLevel;
    private int speed;
    private int waitingTime;
    private String[]levels;
    private int currentPosition;

    public Lift(String arg, String arg1, String arg2, String arg3, ArrayBlockingQueue<Integer> ext, ArrayBlockingQueue<Integer> inside) {
        this.ext = ext;
        this.inside = inside;
        this.level = Integer.parseInt(arg);
        this.heightLevel = Integer.parseInt(arg1);
        this.speed = Integer.parseInt(arg2);
        this.waitingTime = Integer.parseInt(arg3);
        init(level);
    }

    /**
     * Если очереди пусты лифт спит.
     */
    @Override
    public void run() {
        while (true) {
            try {
                while (inside.isEmpty() && ext.isEmpty()) {
                    TimeUnit.MILLISECONDS.sleep(500);
                }
                if (!ext.isEmpty()) {
                    int floor = ext.take();
                    if (floor == 0) {
                        break;
                    }
                    move(floor);
                }
                if (!inside.isEmpty()) {
                    int floor = inside.take();
                    move(floor);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Метод для управления дверьми лифта.
     * @param waitingTime время ожидания между открытием и закрытием дверей.
     */
    public void openClose(int waitingTime) {
        try {
            System.out.println("Лифт открыл двери.");
            TimeUnit.SECONDS.sleep(waitingTime);
            System.out.println("Лифт закрыл двери.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Медод инициализирует этажи здания в соответствии с полученными параметрами.
     * @param level колличество этажей.
     */
    public void init(int level) {
        this.levels = new String[level];
        for (int i = 0; i < levels.length; i++) {
            levels[i] = String.format("Лифт проежает этаж %d", i + 1);
        }
    }

    /**
     * Метод производит перемещение лифта в заданном направлении.
     * @param rs требуемый этаж.
     */
    public void move(int rs) {
        try {
            rs = rs - 1;
            if (rs == currentPosition) {
                openClose(waitingTime);
            } else {
                if (rs > currentPosition) {
                    for (int i = currentPosition; i < rs; i++) {
                        TimeUnit.SECONDS.sleep(heightLevel / speed);
                        System.out.println(levels[i]);
                    }
                    openClose(waitingTime);
                    this.currentPosition = rs;
                } else {
                    for (int i = currentPosition; i > rs; i--) {
                        System.out.println(levels[i]);
                        TimeUnit.SECONDS.sleep(heightLevel / speed);
                    }
                    openClose(waitingTime);
                    this.currentPosition = rs;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
