package  ru.job4j.multithreading.countdown;

import java.util.concurrent.CountDownLatch;

/**
 * .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 18.11.17;
 * @version $Id$
 * @since 0.1
 */
public class Rase {
    private final CountDownLatch start = new CountDownLatch(8);
    /**
     * Гоночная трасса.
     */
    private final int trackLength = 500000;

    public CountDownLatch getStart() {
        return start;
    }

    public int getTrackLength() {
        return trackLength;
    }

    public static void main(String[] args) throws InterruptedException {
        Rase rase = new Rase();
        for (int i = 0; i != 5; i++) {
            new Thread(new Car(i, ((int) Math.random() * 100 + 50), rase)).start();
            Thread.sleep(1000);
        }
        while (rase.getStart().getCount() > 3) {
            Thread.sleep(100);
        }
        Thread.sleep(1000);
        System.out.println("На старт!");
        rase.getStart().countDown();

        Thread.sleep(1000);
        System.out.println("Внимание !");
        rase.getStart().countDown();

        Thread.sleep(1000);
        System.out.println("Марш !");
        rase.getStart().countDown();
    }
}
