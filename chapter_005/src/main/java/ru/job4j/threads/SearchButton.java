package ru.job4j.threads;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class SearchButton implements Searchable {
    private String name = "Поиск пробелов";
    private String path;
    private int count;

    public SearchButton(String path) {
        this.path = path;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void search() throws InterruptedException {
        int str;
        try (BufferedReader rsc = new BufferedReader(new FileReader(path))) {
            while ((str = rsc.read()) != -1) {
                if (Character.isWhitespace((char) str)) {
                    count++;
                    System.out.println("Пробел");
                    Thread.sleep(1);
                }
            }
            System.out.println(String.format("Общее количество пробелов: %d", count));
        } catch (IOException e) {
            System.out.println("Error of I/O");
        }
    }
}
