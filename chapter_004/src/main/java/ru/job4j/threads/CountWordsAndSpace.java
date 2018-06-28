package ru.job4j.threads;

public class CountWordsAndSpace {

    public CountWordsAndSpace(String text) {

        new Thread() {
            @Override
            public void run() {
                char[] chars = text.toCharArray();
                int count = 0;
                for (int i = 0; i < chars.length; i++) {
                    if (chars[i] == ' ') {
                        count++;
                    }
                }
                System.out.println("Spaces = " + count);
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                int wordCount = text.split(" ").length;
                System.out.println("Words = " + wordCount);
            }
        }.start();

        try {
            Thread.currentThread().sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
