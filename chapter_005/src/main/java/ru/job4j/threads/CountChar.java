package ru.job4j.threads;

public class CountChar implements Runnable {
    String str;
    int length;

    public CountChar(String str) {
        this.str = str;
    }

    @Override
    public void run() {
        char[] arr = str.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != ' ') {
                length++;
            }
            if (Thread.interrupted()) {
                System.out.println("Interrupted!" + length);
                return;
            }
        }
        System.out.println(length);
    }
}
