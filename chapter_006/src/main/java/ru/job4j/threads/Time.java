package ru.job4j.threads;

public class Time implements Runnable {
    private long timeLimit;
    private String filePath;

    public Time(int timeLimit, String filePath) {
        this.timeLimit = timeLimit;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        Thread countChar = new Thread(new CountChar(filePath));
        countChar.start();
        try {
            countChar.join(timeLimit);
            countChar.interrupt();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}
