package ru.job4j.multithreading.threads.stoptreads;

import java.io.*;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 06.11.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class CountChar implements Runnable {
    @Override
    public void run() {
        System.out.println("Читаем файл");
        BufferedReader reader;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(new File("C:/projects/hincuA/chapter_007/king.txt")));
            while (reader.ready()) {
                sb.append(reader.readLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        char[] chars = sb.toString().toCharArray();
        int count = 0;
        System.out.println("Считаем символы.");
        for (int i = 0; i < chars.length; i++) {
            count++;
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Времени не достаточно.Счет прерван.");
                break;
            }
        }
        System.out.println(String.format("Символов посчитано: %d", count));
    }
}
