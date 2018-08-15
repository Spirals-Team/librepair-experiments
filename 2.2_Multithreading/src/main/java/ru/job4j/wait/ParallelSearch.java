package ru.job4j.wait;

import net.jcip.annotations.ThreadSafe;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@ThreadSafe
public class ParallelSearch {
    /**
     * @param root start folder for searching
     */
    private final String root;
    /**
     * @param text text for searching
     */
    private final String text;
    /**
     * @param exts file extension
     */
    private final List<String> exts;
    /**
     * @param finish checking the status of the search thread
     */
    private volatile boolean finish = false;
    /**
     * @param paths paths all required files
     */
    private final List<String> paths = new ArrayList<>();
    /**
     * @param files temporary storage for uncheked files
     */
    private final BlockingQueue<Path> files = new LinkedBlockingQueue<>();


    /**
     * Constructor.
     * @param root start folder for find
     * @param text text for seraching files
     * @param exts extensions by files
     */
    public ParallelSearch(String root, String text, List<String> exts) {
        this.root = root;
        this.text = text;
        this.exts = exts;
    }

    /**
     * Method for starting search.
     * @throws InterruptedException exception
     */
    public void init() throws InterruptedException {

        Thread search = new Thread() {
            private final FindFiles ff = new FindFiles(exts, files);
            private final File file = new File(root);
            private final Path path = file.toPath();

            @Override
            public void run() {
                try {
                    Files.walkFileTree(path, ff);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finish = true;
            }
        };

        Thread read = new Thread(() -> {
            while (!finish || !files.isEmpty()) {
                try {
                    Path path = files.take();
                    String temp = new String(Files.readAllBytes(path));
                    if (temp.contains(text)) {
                        paths.add(path.toString());
                    }
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        search.start();
        read.start();
        search.join();
        read.join();
    }

    /**
     * Getter.
     * @return return list by paths
     */
     public List<String>  result() {
        return this.paths;
    }
}

