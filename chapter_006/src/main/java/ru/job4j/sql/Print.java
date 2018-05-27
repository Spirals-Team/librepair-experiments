package ru.job4j.sql;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Print {

    public void printDate(String text) {
        int max = 50 - text.length();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        System.out.print(text);
        for (int i = 0; i < max; i++) {
            System.out.print(".");
        }
        System.out.println(format.format(new Date()));
    }
}
