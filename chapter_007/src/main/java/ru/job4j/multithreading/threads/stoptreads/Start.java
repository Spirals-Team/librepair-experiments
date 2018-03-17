package ru.job4j.multithreading.threads.stoptreads;

/**
 * Start.
 * @author Hincu Andrei (andreih1981@gmail.com) by 07.11.17;
 * @version $Id$
 * @since 0.1
 */
public class Start {
    public static void main(String[] args) {
        int totalTime = 10;
        CountChar countChar = new CountChar();
        Thread charterCountThread = new Thread(countChar);
        charterCountThread.start();
        Time time = new Time(totalTime, charterCountThread);
        Thread thread = new Thread(time);
        thread.start();
        try {
            charterCountThread.join();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Программа завершает работу.");
    }
}
