package  ru.job4j.multithreading.jmm;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Класс с методом майн.
 * @author Hincu Andrei (andreih1981@gmail.com)on 07.11.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class CountTester {
    public static void main(String[] args) throws InterruptedException {
        Count count = new Count();
        for (int i = 0; i < 200; i++) {
            CountThread countThread = new CountThread(count);
            countThread.start();
        }
        Thread.sleep(1000);
        System.out.println(count.getCount());
    }
}

/**
 * Класс счетчик который используют потоки.
 */
@ThreadSafe
class Count {
    /**
     * счетчик.
     */
    @GuardedBy("this")
    private long count = 0;

    /**
     * Метод прибавляет значение к счетчику.
     */
    void increment() {
        synchronized (this) {
            count += (long) 1;
        }
    }
    @GuardedBy("this")
    long getCount() {
        synchronized (this) {
            return count;
        }
    }

}

/**
 * Класс нити.
 */
class CountThread extends Thread {
    /**
     * ссылка на счетчик.
     */
    private final Count count;


    public CountThread(final Count count) {
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            count.increment();
        }
    }
}

