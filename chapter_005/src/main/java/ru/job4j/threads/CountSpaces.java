package ru.job4j.threads;

public class CountSpaces implements Runnable {
    String str;

    public CountSpaces(String str) {
        this.str = str;
    }

    @Override
    public void run() {
        char[] arr = str.toCharArray();
        int countSpaces = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == ' ') {
                countSpaces++;
            }
        }
        System.out.println(countSpaces);
    }
}
