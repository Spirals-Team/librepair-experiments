package ru.skorikov;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 07.02.18
 * @ version:
 * Класс работа.
 */
public class Work implements Runnable {
    /**
     * Какая то полезная переменная.
     */
    private AtomicInteger j = new AtomicInteger();

    //Полезный метод.
    @Override
    public void run() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            //Не знаю - можно так- нет? - вместо печати стектрейса остановить поток
            //e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        j.incrementAndGet();
        System.out.println("It's work " + j);
    }
}
