package ru.job4j.threads;

public class Print implements Runnable {
    private String str;

    public Print(String str) {
        this.str = str;
    }

    @Override
    public void run() {
        System.out.println(str);
    }
}
