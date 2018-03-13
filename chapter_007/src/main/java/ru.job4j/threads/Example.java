package ru.job4j.threads;

class JoinClass implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (!Thread.interrupted()) {
                System.out.println(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException!");
                    System.err.println("Exception msg: " + e.getMessage());
                    return;
                }
            } else {
                System.out.println("Interrupted!");
                return;
            }
        }
    }
}

public class Example {

    public static void main(String[] args) {
        Thread thread = new Thread(new JoinClass());
        thread.start();
        try {
            Thread.sleep(1000L); // выключаем поток main, чтобы в JoinClass.run() что-то успело посчитаться
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }
}
