package ru.job4j.multithreading.poducer;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 12.11.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class FromLesson {
    private final Object lock = new Object();
    private boolean blockConsumir = true;

    public void doSomething() {
        synchronized (this.lock) {
            while (this.blockConsumir) {
                try {
                    System.out.println(String.format("%d ждет - wait", Thread.currentThread().getId()));
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(String.format("%d Выполняет работу", Thread.currentThread().getId()));
        }
    }

    public void changeBlock(boolean en) {
        synchronized (this.lock) {
            this.blockConsumir = en;
            System.out.println(String.format("%d вызван notify", Thread.currentThread().getId()));
            this.lock.notify();
        }

    }

    public static void main(String[] args) {
        FromLesson lesson = new FromLesson();
        Thread product = new Thread() {
            @Override
            public void run() {
                lesson.doSomething();
            }
        };
        Thread consumir = new Thread() {
            @Override
            public void run() {
                lesson.changeBlock(false);
            }
        };
        product.start();
        consumir.start();
    }
}
