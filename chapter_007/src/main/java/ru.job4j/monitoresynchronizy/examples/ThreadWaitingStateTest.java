package ru.job4j.monitoresynchronizy.examples;

public class ThreadWaitingStateTest extends Thread {


    public void run() {

        try {

            synchronized (this) {

                wait();

            }

        } catch (InterruptedException e) {

            System.err.print("ошибка потока");

        }

    }

    public static void main(String[] args) {

        try {

            Thread thread = new ThreadWaitingStateTest();

            thread.start();

            synchronized (thread) {

                Thread.sleep(10);

// BLOCKED – because thread attempting to acquire a lock

                System.out.println("1: " + thread.getState());

            }

            Thread.sleep(10);

// WAITING – метод wait() внутри synchronized

// останавил поток и освободил блокировку

            System.out.println("2: " + thread.getState());

            thread.interrupt();

        } catch (InterruptedException e) {

            System.err.print("ошибка потока");

        }

    }

}
