package ru.job4j.threads;
/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class WordsAndSpaces {

    public WordsAndSpaces(String text) {
        Thread space = new Thread() {
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
        };

        Thread word = new Thread() {
            @Override
            public void run() {
                int wordCount = text.split(" ").length;
                System.out.println("Words = " + wordCount);
            }
        };

        System.out.println("Программа считает число пробелов и слов в передаваемой строке");
        word.start();
        space.start();
        try {
            word.join();
            space.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Программа завершила свою работу");
    }
}
