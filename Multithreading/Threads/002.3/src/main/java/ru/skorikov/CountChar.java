package ru.skorikov;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 25.12.17
 * @ version: java_kurs_standart
 */
public class CountChar implements Runnable {

    @Override
    public void run() {
        System.out.println("Запущен поток подсчета символов.");
        String path = new File("").getAbsolutePath();
        File file = new File(path, "Skazka.txt");
        int count = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String str;
            while ((str = reader.readLine()) != null) {
                if (!Thread.currentThread().isInterrupted()) {
                    str = str.replaceAll(" ", "");
                    count += str.length();
                } else {
                    System.out.println("Время вышло.");
                    break;
                }
            }
            System.out.println("Посчитано " + count + " символов.");
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        System.out.println("Поток подсчета символов завершен.");
    }
}
