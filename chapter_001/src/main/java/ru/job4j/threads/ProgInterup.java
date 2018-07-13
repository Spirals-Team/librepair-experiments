package ru.job4j.threads;

/**
 * Класс содержит два внутренних класса для демонстрации прерывания одного потока другим через определенное время
 */
public class ProgInterup {
    // поток который надо прервать
    private Thread thread;
    // таймер окончания работы первого потока
    private long timer;
    // текст для работы второго потока
    private String text;
    // количество символов подсчитанных вторым потоком в тексте

    /**
     * Конструктор
     * @param text текст для подсчета
     * @param time время работы первого потока (в мс)
     */
    public ProgInterup (String text, long time) {
        this.text = text;
        this.timer = time;
    }

    public void setTread(Thread thread) {
        this.thread = thread;
    }

    /**
     * Класс определяющий поток работающий определенное время
     */
    public class Time implements Runnable {
        @Override
        public void run() {
            long begintime = System.currentTimeMillis();
            System.out.println(String.format("Begin time. (Timer:%s)", timer));
            while (System.currentTimeMillis() - begintime < timer) {

            }
            System.out.println(String.format("End time. (Timer:%s)", timer));
            thread.interrupt();
        }
    }

    /**
     * Класс определяющий поток, подсчитывающий количество символов в тексте
     */
    public class CountChar implements  Runnable {
        private int count = 0;

        public int getCount() {
            return count;
        }

        @Override
        public void run() {
            System.out.println(String.format(" Begin chars count. (Timer: %s)", timer));
            for (int i = 0; i < text.length() && ! Thread.interrupted(); i++) {
                count++;
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    System.out.println(String.format("Chars count interrupt. (Timer:%s)", timer));
                    break;
                }
            }
            if (Thread.interrupted()) {
                System.out.println(String.format("Chars count interrupt. (Timer:%s)", timer));
            }
            System.out.println( String.format("End chars count. %s chars count. (Timer:%s)", count, timer));
        }
    }
}
