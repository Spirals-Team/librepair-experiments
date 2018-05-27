package ru.job4j.threads;


/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class CountChar {
    public void counter(String st) {
        System.out.println("Start analiz...");
        Thread threadChar = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for (int i = 0; i < st.length(); i++) {
                    if (st.charAt(i) != 32) {
                        sum++;

                    }
                }
                System.out.println(String.format("Sum char = %d", sum));
            }
        });
        Thread threadSpace = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for (int i = 0; i < st.length(); i++) {
                    if (st.charAt(i) == 32) {
                        sum++;

                    }
                }
                System.out.println(String.format("Sum space = %d", sum));
            }
        });
        threadChar.start();
        threadSpace.start();
        try {
            threadChar.join();
            threadSpace.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Analiz finish.");    }

    public static void main(String[] args) {
        CountChar countChar = new CountChar();
        String st = "Создать программу, которая будет считать количество слов и пробелов в тексте.";
        countChar.counter(st);
    }
}

