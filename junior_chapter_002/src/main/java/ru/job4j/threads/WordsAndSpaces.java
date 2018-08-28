package ru.job4j.threads;

public class WordsAndSpaces {
    public static class Words extends Thread {
        String text;

        public Words(String text) {
            this.text = text;
        }

        @Override
        public void run() {
            String[] array = text.split(" ");
            System.out.println("Колличество слов в тексте: " + array.length);
        }
    }

    public static class Spaces extends Thread {
        String text;

        public Spaces(String text) {
            this.text = text;
        }

        public void run() {
            char[] chars = text.toCharArray();
            int counts = 0;
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == ' ') {
                    counts++;
                }
            }
            System.out.println("Количсетво пробелов в тексте: " + counts);
        }
    }


    public static void main(String[] args) {
        System.out.println("Start programm!!!");
        Thread thread1 = new Words("Привет как дела ?");
        thread1.start();
        Thread thread2 = new Spaces("Привет как дела ?");
        thread2.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("End programm!!!");
    }
}
