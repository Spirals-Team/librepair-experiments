package ru.job4j.threads;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CountChar implements Runnable {
    private String filePath;

    public CountChar(String path) {
        this.filePath = path;
    }

    public int countChar() {
        int count = 0;
        try(BufferedReader buffer = new BufferedReader(new FileReader(filePath))) {
            while(buffer.read() != -1) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Thread was interrupted!!!");
                    break;
                }
                count++;
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        return count;
    }

    @Override
    public void run() {
        System.out.printf("Count of chars: %d\n", countChar());
    }
}
