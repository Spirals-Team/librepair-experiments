package ru.job4j.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class ParallelSearch {
    private final String root;
    private final String text;
    private final List<String> exts;
    volatile boolean finish = false;

    @GuardedBy("this")
    private final Queue<String> files = new LinkedList<>();

    @GuardedBy("this")
    private final List<String> paths = new ArrayList<>();

    public ParallelSearch(String root, String text, List<String> exts) {
        this.root = root;
        this.text = text;
        this.exts = exts;
    }

    public void init() {
        Thread search = new Thread() {
            @Override
            public void run() {
                Path path = Paths.get(root);
                try {
                    Files.walkFileTree(path, new MyFileVisitor());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                finish = true;
                synchronized (files) {
                    files.notify();
                }
            }
        };
        Thread read = new Thread() {
            @Override
            public void run() {
                Path path;
                while (!finish) {
                    if (!files.isEmpty()) {
                        synchronized (files) {
                            path = Paths.get(files.poll());
                        }
                        if (Files.isReadable(path)) {
                            try {
                                String content = new String(Files.readAllBytes(path));
                                if (text != null && content.indexOf(text) != -1) {
                                    synchronized (paths) {
                                        paths.add(path.toString());
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        try {
                            synchronized (files) {
                                files.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        search.start();
        read.start();
        try {
            search.join();
            read.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized List<String> result() {
        return this.paths;
    }

    class MyFileVisitor implements FileVisitor<Object> {
        @Override
        public FileVisitResult preVisitDirectory(Object dir, BasicFileAttributes attrs) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {
            for (String ends : exts) {
                if (file.toString().endsWith(ends)) {
                    synchronized (files) {
                        files.add(file.toString());
                        files.notify();
                    }
                    break;
                }
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Object file, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Object dir, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        List<String> ext = new ArrayList<>();
        ext.add(".txt");

        ParallelSearch search = new ParallelSearch("C:\\", "t", ext);
        search.init();
        List<String> list = search.result();
        for (String s : list) {
            System.out.println(s);
        }

    }
}
