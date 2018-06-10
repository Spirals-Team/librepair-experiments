package ru.job4j.threads;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Integer.MAX_VALUE;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class ParallelSearch {

    private final String root;
    private final String text;
    private final List<String> exts;
    private volatile boolean finish = false;

    @GuardedBy("this")
    private final BlockingQueue<String> files = new LinkedBlockingQueue<>();

    @GuardedBy("this")
    private final List<String> paths = new ArrayList<>();


    public ParallelSearch(String root, String text, List<String> exts) {
        this.root = root;
        this.text = text;
        this.exts = exts;
        init();
    }

    private void init() {
        Thread search = new Thread() {
            @Override
            public void run() {
                try {
                    Files.walkFileTree(Paths.get(root), new HashSet<FileVisitOption>(), MAX_VALUE, new MyFileVisitor());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finish = true;
            }
        };
        try {
            search.start();
            search.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread read = new Thread() {
            @Override
            public void run() {
                synchronized (files) {
                    if (!finish) {
                        Thread.currentThread().interrupt();
                    }
                    while (!files.isEmpty()) {
                        try {
                            String s = files.take();
                            if (new String(Files.readAllBytes(Paths.get(s))).contains(text)) {
                                paths.add(s);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        try {
            read.start();
            read.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public synchronized List<String>  result() {
        return this.paths;
    }

    class MyFileVisitor extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            for (String ex : exts) {
                if (file.toString().endsWith(ex)) {
                    try {
                        files.put(file.toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return FileVisitResult.CONTINUE;
        }
    }
}