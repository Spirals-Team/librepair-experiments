package ru.job4j.threads;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleLock {
    private boolean locker = false;

    public void lock() {
       synchronized (this) {
           if (!locker) {
               locker = true;
           } else {
               try {
                   Thread.sleep(10);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }

           }
       }
    }

    public void unlock() {
        synchronized (this) {
            locker = false;
            Thread.interrupted();
        }
    }
}
