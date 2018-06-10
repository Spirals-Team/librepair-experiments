package ru.job4j.threads;

public class ProgrammInterrupt {

    public ProgrammInterrupt(String text, long timeSpent) {

        long startTime = System.currentTimeMillis();

        Thread count = new Thread() {
            @Override
            public void run() {
                    char[] chars = text.toCharArray();
                    int count = 0;
                    for (int i = 0; i < chars.length; i++) {
                        if (chars[i] == ' ') {
                            if (!isInterrupted()) {
                                count++;
                            }
                        }
                    }
                    System.out.println("Spaces = " + count);
            }
        };

        Thread time = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    if (System.currentTimeMillis() - startTime > timeSpent) {
                        count.interrupt();
                        Thread.currentThread().interrupt();
                    }
                }
            }
        };

        count.start();
        time.start();
        try {
            time.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
