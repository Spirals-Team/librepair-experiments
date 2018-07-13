package ru.job4j.threads;

/**
 * Класс определяет два внутренних класса : один для подсчета пробелов а другой для подсчета слов в переданном тексте.
 */
public class Threads {

    // переданный текст
    private String text;
    int counts;
    int countw;

    // конструктор с параметром
    public Threads(String text) {
        this.text = text;
    }

    /**
     *  Класс подсчитывает количество пробелов в тексте, выводя счетчик после каждого найденного пробела
     */
    public class countSpaces implements Runnable  {
        @Override
        public void run() {
            counts = 0;
            char [] txt = text.toCharArray();
            System.out.println("Begin space's count");
            for (int i = 0; i < txt.length; i++) {
                if (txt[i] == (char) 32) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(String.format("Spaces: %s", ++counts));
                }
            }
            System.out.println("End space's count");
        }
    }

    /**
     *  Класс подсчитывает количество слов в тексте, выводя счетчик после каждого найденного слова
     */
    public class countWords implements Runnable  {
        @Override
        public void run() {
            countw = 0;
            char [] txt = text.toCharArray();
            System.out.println("Begin word's count");
            for (int i = 1; i < txt.length; i++) {
                if ( (txt[i] == (char) 46 || txt[i] == (char) 32) && txt[i-1] != (char) 32 && txt[i-1] != (char) 46) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(String.format("Words: %s", ++countw));
                }
            }
            System.out.println("End word's count");
        }
    }
}
