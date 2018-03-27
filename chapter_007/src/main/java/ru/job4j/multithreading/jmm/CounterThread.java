package  ru.job4j.multithreading.jmm;

/**
 * Нить вызывает инкремент 1000 раз.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 07.11.17;
 * @version $Id$
 * @since 0.1
 */
public class CounterThread extends Thread {
    private Counter counter;
    private Thread thread;

    CounterThread(Counter counter) {
        this.counter = counter;
        this.thread = new Thread(this);
        this.thread.start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            counter.increment();
        }
    }
}
