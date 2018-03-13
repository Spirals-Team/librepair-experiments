package ru.job4j.monitoresynchronizy.examples;

import java.util.concurrent.Callable;

public class RunVsCalEx {

    public static void main(String[] args) throws Exception {
        Runnable callback1 = new Runnable() {

            @Override
            public void run() {
            }
        };
        process(callback1);

        Callable<String> callback2 = new Callable<String>() {

            @Override
            public String call() throws Exception {
                String str = "String from callback";
                return str;
            }
        };
        processString(callback2);
    }

    private static void process(Runnable callback) {
        for (int i = 0; i <= 100; i++) {
            if (i == 100) {
                callback.run();
            }
        }
    }

    private static void processString(Callable<String> callback)
            throws Exception {
        for (int i = 0; i <= 100; i++) {
            if (i == 100) {
                callback.call();
            }
        }
    }

}