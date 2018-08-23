package ru.job4j.jmm;

/**
 * Class RaceProblem.
 */
public class RaceProblem {
    /**
     * @param count for save value
     */
    private int count = 0;

    /**
     * Method which increase current value
     */
    public void increaseCount() {
        this.count++;
    }

    /**
     * Getter for count
     * @return count
     */
    public int getCount() {
        return count;
    }

    /**
     * Method main.
     * @param args args
     */
    public static void main(String[] args) {
        RaceProblem rp = new RaceProblem();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100000; i++) {
                    rp.increaseCount();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100000; i++) {
                    rp.increaseCount();
                }
            }
        });

        thread1.start();
        thread2.start();

        while (thread1.isAlive() || thread2.isAlive()) {
            continue;
        }
        System.out.println("Counter: " + rp.getCount());
    }
}
