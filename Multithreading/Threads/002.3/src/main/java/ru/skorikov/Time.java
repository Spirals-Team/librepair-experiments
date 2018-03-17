package ru.skorikov;

import java.util.Date;


/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 25.12.17
 * @ version: java_kurs_standart
 */
public class Time implements Runnable {
    /**
     * Время начала работы программы.
     */
    private long start = new Date().getTime();
    /**
     * Время отпущенное на работу в миллисекундах.
     */
    private long finish;

    /**
     * Конструктор.
     * @param finish время работы.
     */
    public Time(long finish) {
        this.finish = finish;
    }

    @Override
    public void run() {
        System.out.println("Запущен поток отсчета времени.");
        long endWork = new Date().getTime();
        while ((start + finish) > endWork) {
            if (!Thread.currentThread().isInterrupted()) {
                endWork = new Date().getTime();
            } else {
                System.out.println("Символы кончились раньше. Время не вышло.");
                break;
            }
        }
        System.out.println("Поток отсчета времени завершен.");
    }
}
