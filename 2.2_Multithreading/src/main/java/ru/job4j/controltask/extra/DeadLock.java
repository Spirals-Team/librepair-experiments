package ru.job4j.controltask.extra;

/**
 *  Class DeadLock
 */
public class DeadLock {
    /**
     * @param A Object for lock
     */
    private Object a = new Object();
    /**
     * @param B Object for lock
     */
    private Object b = new Object();
    /**
     * @param next check status
     */
    private boolean next = false;

    /**
     * Method A.
     * @throws InterruptedException exception
     */
    public void methodA() throws InterruptedException {
        synchronized (a) {
            synchronized (b) {
                while (!next) {
                    b.wait();
                }
                System.out.println(Thread.currentThread().getName() + " is stopped");
            }
        }
    }


    /**
     * Method B.
     * @throws InterruptedException exception.
     */
    public void methodB() throws InterruptedException {
        synchronized (a) {
            synchronized (b) {
                next = true;
                notifyAll();
                System.out.println(Thread.currentThread().getName() + " is stopped");
            }
        }
    }

    /**
     * Main method.
     * @param args args
     * @throws InterruptedException exception
     */
    public static void main(String[] args) throws InterruptedException {
        DeadLock dl = new DeadLock();
        Thread thA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dl.methodA();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thB = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dl.methodB();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thA.start();
        thA.join(100);
        thB.start();
        thB.join();
    }
}
