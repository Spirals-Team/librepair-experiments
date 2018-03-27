package ru.job4j.multithreading;

import java.io.*;
import java.util.*;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 10.11.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class ParallerSearch {
    /**
     * Лист с результатом работы программы.
     */
    private final List<String> result = new ArrayList<>();
    /**
     * Очередь для обработки файлов с требуемым расширением.
     */
    private final Queue<File> files = new ArrayDeque<>();
    /**
     * Начальная папка окуда следует начать поиск.
     */
    private final String root;
    /**
     * Текст который необходимо найти в файлах.
     */
    private final String text;
    /**
     * Список расширений файлов в которых требуется производить поиск.
     */
    private final List<String> exts;
    /**
     * Нить для поиска файлов нужного расширения.
     */
    private Thread searchFiles = new Thread(new Runnable() {
        @Override
        public void run() {
            File file = new File(root);
            search(file);
        }
    });
    /**
     * Нить для поиска текста в найденных файлах.
     */
    private Thread searchText = new Thread(new Runnable() {

        @Override
        public void run() {
            while (searchFiles.isAlive() || !files.isEmpty()) {
                File file;
                synchronized (files) {
                    if (files.isEmpty()) {
                        try {
                            System.out.println("Нить ждет добавления файлов в очередь.");
                            files.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    file = files.poll();
                    System.out.println("Забираем файл из очереди.");
                }
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    while (reader.ready()) {
                        String s = reader.readLine();
                        if (s.contains(text)) {
                            result.add(file.getPath());
                            System.out.println("Текст в файле найден, добавляем файл в результат.");
                            break;
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
        }
    });

    public ParallerSearch(String root, String text, List<String> exts) {
        this.root = root;
        this.text = text;
        this.exts = exts;
    }

    public static void main(String[] args) {
       // String root = "/home/andrei/cod";
        String root = "D://cod";
        String text = "hello world";
        List<String> exts = Arrays.asList("txt", "log");
        ParallerSearch parallerSearch = new ParallerSearch(root, text, exts);
        parallerSearch.searchFiles.start();
        parallerSearch.searchText.start();
        while (parallerSearch.searchText.isAlive()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(parallerSearch.result);
    }

    /**
     * Метод рекурсивного поиска файлов в заданной директории.
     * @param file начальная папка.
     */
    private void search(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isDirectory()) {
                    search(f);
                } else if (f.isFile()) {
                    String name = f.getName();
                    if (name.contains(".")) {
                        name = name.split("\\.")[1];
                        if (exts.contains(name)) {
                            synchronized (this.files) {
                                this.files.add(f);
                                System.out.println("Файл соответствующего типа найден.");
                                try {
                                    Thread.sleep(100);
                                    //для имитации работы с большими файлами
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                this.files.notify();
                            }
                        }
                    }
                }
            }
        }
    }
}
