package ru.job4j.monitoresynchronizy.examples;

class T extends Thread {

    public void run() {

        try {

            if (isDaemon()) {

                System.out.println("старт потока-демона");

                sleep(10000); // заменить параметр на 1

            } else {

                System.out.println("старт обычного потока");

            }

        } catch (InterruptedException e) {

            System.err.print("Error" + e);

        } finally {

            if (!isDaemon()) {

                System.out.println(

                        "завершение обычного потока");

            } else {

                System.out.println(

                        "завершение потока-демона");

            }



        }

    }

}


public class DemoDaemonThread {

    public static void main(String[] args) {

        T usual = new T();

        T daemon = new T();

        daemon.setDaemon(true);

        daemon.start();

        usual.start();

        System.out.println(

                "последний оператор main");

    }

}
