package ru.job4j.threads;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class SearchWord implements Searchable {
    private String name = "Поиск слов";
    private String path;
    private int count;

    public SearchWord(String path) {
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
                while (str != 32) {
                    str = rsc.read();
                    if (str == 32 | str == -1) {
                        count++;
                        System.out.println("Слово");
                        Thread.sleep(2);
                        break;
                    }
                }
            }
            System.out.println(String.format("Общее количество слов: %d", count));
        } catch (IOException e) {
            System.out.println("Error of I/O");
        }
    }
}
