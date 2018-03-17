package ru.job4j.multithreading.poducer;

/**
 * Тест notify() and wait().
 * @author Hincu Andrei (andreih1981@gmail.com)on 12.11.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class ProducerConsumer {
    public static void main(String[] args) {
        Store store = new Store();
        new Thread(new Product(store)).start();
        new Thread(new Consum(store)).start();

    }
}

/**
 * Класс продавца.
 */
class Product implements Runnable {
    private Store store;
    public Product(Store store) {
        this.store = store;
    }

    @Override
    public void run() {
        for (int i = 0; i < 6; i++) {
            store.put();
        }
    }
}

/**
 * Класс покупателя.
 */
class Consum implements Runnable {
    private Store store;

    public Consum(Store store) {
        this.store = store;
    }

    @Override
    public void run() {
        for (int i = 0; i < 6; i++) {
            store.get();
        }
    }
}

/**
 * Магазин. Может хранить до 3 товаров.
 */
class Store {
    private int product = 0;
    public synchronized void get() {
        while (product < 1) {
            try {
                System.out.println("Ждем пока привизут товар в магазин.");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        product--;
        System.out.println("Покупатель приобрел 1 товар.");
        System.out.println("Товаров на складе" + product);
        notify();

    }
    public synchronized void put() {
        while (product > 3) {
            try {
                System.out.println("Магазин не может вместить больше товаров.");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        product++;
        System.out.println("Производитель добавил 1 товар");
        System.out.println("Товаров на складе " + product);
        notify();

    }
}


