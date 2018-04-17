package ru.job4j.threads;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class ThreadTest {

    @Test
    public void testThread() {
        String path = "..\\.gitignore";
        System.out.format("Программа по подсчету пробелов и слов в текстовом файле: %s%n", path);
        NewThread tread1 = new NewThread(new SearchWord(path));
        NewThread tread2 = new NewThread(new SearchButton(path));

        try {
            tread1.getThread().join();
            tread2.getThread().join();
        } catch (InterruptedException e) {
            System.out.println("Главный поток прерван");
        }
        System.out.println("Программа завершена");
    }
}