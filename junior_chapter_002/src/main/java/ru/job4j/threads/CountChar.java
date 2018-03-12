package ru.job4j.threads;

public class CountChar implements Runnable {
    String text;

    public CountChar(String text) {
        this.text = text;
    }

    @Override
    public void run() {
        System.out.println("Start Thread CountChar");
        char[] chars = text.toCharArray();
        int counts = 0;
        for (int i = 0; i < chars.length; i++) {
            counts++;
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Операция прервана. Кончелось время.");
                break;
            }
        }
        System.out.println("Количсетво символов в тексте: " + counts);
        System.out.println("End Thread CountChar");
    }
}
