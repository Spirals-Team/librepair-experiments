package ru.job4j.threads;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Counter {
    private String path;

    public Counter(String path) {
        this.path = path;
    }

    public int countWords() {
        int ch;
        int count = 0;
        boolean isChar = true;
        try (BufferedReader buffer = new BufferedReader(new FileReader(path))) {
            while ((ch = buffer.read()) != -1) {
                if ((char) ch == ' ' || (char) ch == '\n') {
                    if (isChar) {
                        count++;
                        isChar = false;
                    }
                } else {
                    isChar = true;
                }
            }
            if (isChar) {
                count++;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return count;
    }

    public int countSpaces() {
        int ch;
        int count = 0;
        try (BufferedReader buffer = new BufferedReader(new FileReader(path))) {
            while ((ch = buffer.read()) != -1) {
                if ((char) ch == ' ') {
                    count++;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return count;
    }

    public static void main(String[] args) {
        Counter counter = new Counter("text.txt");
        Thread spacesCount = new Thread(() ->  System.out.printf("Spaces: %d\n", counter.countSpaces()));
        Thread wordsCount = new Thread(() ->  System.out.printf("Words: %d\n", counter.countWords()));
        wordsCount.start();
        spacesCount.start();
        try {
            System.out.println("Start");
            spacesCount.join();
            wordsCount.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finish");
    }
}
