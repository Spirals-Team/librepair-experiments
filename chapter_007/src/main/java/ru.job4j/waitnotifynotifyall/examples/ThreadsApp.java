package ru.job4j.waitnotifynotifyall.examples;

//https://metanit.com/java/tutorial/8.5.php
public class ThreadsApp {

    public static void main(String[] args) {

        Store store = new Store();
        Producer producer = new Producer(store);
        Consumer consumer = new Consumer(store);
//        new Thread(producer).start();
//        new Thread(consumer).start();

        Thread threadConsumer = new Thread(consumer);
        Thread threadProducer = new Thread(producer);

        threadProducer.start();
        threadConsumer.start();
    }
}
// Класс Магазин, хранящий произведенные товары
class Store {
    private int product = 0;
    public synchronized void get() {
        while (product < 1) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        product--;
        System.out.println("Покупатель купил 1 товар");
        System.out.println("Товаров на складе: " + product);
        notifyAll();
    }
    public synchronized void put() {
        while (product >= 10) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        product++;
        System.out.println("Производитель добавил 1 товар");
        System.out.println("Товаров на складе: " + product);
        notifyAll();
    }
}
// класс Производитель
class Producer implements Runnable {

    Store store;
    Producer(Store store) {
        this.store = store;
    }
    public void run() {
        for (int i = 1; i < 100; i++) {
            store.put();
        }
    }
}
// Класс Потребитель
class Consumer implements Runnable {

    Store store;
    Consumer(Store store) {
        this.store = store;
    }
    public void run() {
        for (int i = 1; i < 100; i++) {
            store.get();
        }
    }
}
