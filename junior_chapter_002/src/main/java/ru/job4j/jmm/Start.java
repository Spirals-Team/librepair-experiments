package ru.job4j.jmm;

public class Start {
    public static void main(String[] args) {
        Count count = new Count();

        Thread thread1 = new Thread(new MyThread(count));
        thread1.start();
        Thread thread2 = new Thread(new MyThread(count));
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // В каждом потоке увеличение переменной в объекте count на 100_000
        // Должно вернуться 200_000
        System.out.println(count.getCount());
        // Но результат будет не предсказуем..
    }
}
