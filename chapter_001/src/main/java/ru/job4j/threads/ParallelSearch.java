package ru.job4j.threads;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@ThreadSafe
/**
 * Класс реализует поиск файлов с заданной точки и по определенным критериям
 * Поиск и первичная фильтрация идет в одном потоке, а окончательная фильтрация в другом
 */
public class ParallelSearch {
    private final String root;
    private final String text;
    private final List<String> exts;
    private volatile boolean finish = false;

    @GuardedBy("this")
    private volatile ConcurrentLinkedQueue<String> files = new ConcurrentLinkedQueue<>();

    @GuardedBy("this")
    private volatile ConcurrentLinkedQueue<String> paths = new ConcurrentLinkedQueue<>();


    /**
     * Конструктор с параметрами
     * @param root узел с какого искать
     * @param text фильтр для имен файлов
     * @param exts фильтр для расширений файлов
     */
    public ParallelSearch(String root, String text, List<String> exts) {
        this.root = root;
        this.text = text;
        this.exts = exts;
    }

    /**
     * Метод для определения и запуска поиска
     */
    public void init() {

        /**
         * Определяем действия для каждого файла при обходе дерева
         */
        FileVisitor<Path> myfile = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (exts.stream().anyMatch(s -> file.getFileName().toString().endsWith(s))) {
                    files.offer(file.toString());
                }
                return FileVisitResult.CONTINUE;
            }
        };

        // поток обходит дерево и ищет нужные файлы
        Thread search = new Thread() {
            @Override
            public void run() {
                System.out.println("Files search starting...");
                try {
                    Files.walkFileTree(Paths.get(root), myfile);
                } catch (IOException e) {
                    System.out.println("Files not found. Check directory...");
                }
                finish = true;
                System.out.println("Files search ended...");
            }
        };

        // поток дополнительно фильтрует файлы найденные первым потоком
        Thread read = new Thread() {
            @Override
            public void run() {
                String s;
                System.out.println("Files filter starting...");
                while (!Thread.currentThread().isInterrupted() || !finish) {
                    while (files.size() > 0) {
                        s = files.poll();
                        if (s.indexOf(text) > -1) {
                            paths.offer(s);
                        }
                    }
                }
                System.out.println("Files search ended...");
            }
        };

        System.out.println(String.format("Parent directory for search:%s Text for search:%s Ext filter:%s", root, text, exts));
        search.start();
        read.start();
    }

    /**
     * Метод возвращает итоговый список файлов
     * @return список файлов
     */
    public Queue<String>  result() {
        return this.paths;
    }

    public boolean isFinish() {
        return finish;
    }
}