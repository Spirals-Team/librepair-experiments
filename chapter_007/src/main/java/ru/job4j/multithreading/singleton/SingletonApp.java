package ru.job4j.multithreading.singleton;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Многопоточный синглтон.
 * @author Hincu Andrei (andreih1981@gmail.com)on 14.01.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class SingletonApp {
    private static final Logger LOG = LogManager.getLogger(SingletonApp.class);

    public static void main(String[] args) throws InterruptedException {
        final int size = 1000;
//        Singleton[] s = new Singleton[size];
//        for (int i =0; i < size; i++) {
//            s[i] = Singleton.getInstance();
//        }

        Thread[] t = new Thread[size];
        for (int i = 0; i < size; i++) {
            t[i] = new Thread(new R());
            t[i].start();
        }
        for (int i = 0; i < size; i++) {
            t[i].join();
        }
        System.out.println(Singleton.count);
    }
}

class R implements Runnable {

    @Override
    public void run() {
        Singleton.getInstance();
    }
}
class Singleton {
    private static final Object SYNC = new Object();
    public static int count = 0;
    private static volatile Singleton instance = null;
    private Singleton() {
        count++;
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (SYNC) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }


    //
//    public static Singleton getInstance() {
//        if (instance == null) {
//            instance = new Singleton();
//        }
//        return instance;
//    }
}
