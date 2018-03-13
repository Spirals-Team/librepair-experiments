package ru.job4j.monitoresynchronizy.examples;

public class ThreadTimedWaitingStateTest extends Thread {

    public void run() {

        try {

            Thread.sleep(50);

        } catch (InterruptedException e) {

            System.err.print("ошибка потока");

        }

    }

    public static void main(String[] args) {

        try {

            Thread thread = new ThreadTimedWaitingStateTest();

// NEW – поток создан, но ещё не запущен

            System.out.println("1: " + thread.getState());

            thread.start();

// RUNNABLE – поток запущен

            System.out.println("2: " + thread.getState());

            Thread.sleep(10);

// TIMED_WAITING

// поток ждет некоторое время окончания работы другого потока

            System.out.println("3: " + thread.getState());

            thread.join();

// TERMINATED – поток завершил выполнение

            System.out.println("4: " + thread.getState());

        } catch (InterruptedException e) {

            System.err.print("ошибка потока");

        }

    }

}
