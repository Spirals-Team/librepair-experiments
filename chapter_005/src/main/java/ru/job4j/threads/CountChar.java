package ru.job4j.threads;

import java.io.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class CountChar implements Runnable {
    private String fileName;
    private int count;
    private Thread thread;

    public CountChar(String fileName) {
        this.fileName = fileName;
        thread = new Thread(this);
        System.out.println("Подсчет слов - ЗАПУСК");
        thread.start();
    }

    public Thread getThread() {
        return thread;
    }

    @Override
    public void run() {
        try (InputStream rsc = new FileInputStream(fileName)) {
            while (rsc.read() != -1) {
                count++;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("Подсчет слов - ЗАВЕРШЕНИЕ");
                    return;
                }
            }
            System.out.format("Общее количество символов: %s%n", count);
        } catch (IOException e) {
            System.out.println("Ошибка I/O");
        }
    }
}
