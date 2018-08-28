package ru.job4j.threads;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.*;
import java.util.*;

@ThreadSafe
public class ParallerSearch {
    @GuardedBy("this")
    private String root;
    private String text;
    private List<String> exts;
    private Queue<File> queueFile = new ArrayDeque<>();
    private List<String> resault = new ArrayList<>();

    public ParallerSearch(String root, String text, List<String> exts) {
        this.root = root;
        this.text = text;
        this.exts = exts;
    }

    private void searchText(String text) {
        while (!queueFile.isEmpty() || threadSearchFile.isAlive()) {
            synchronized (queueFile) {
                File file = queueFile.poll();
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    while (reader.ready()) {
                        if (reader.readLine().contains(text)) {
                            resault.add(file.getPath());
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void searchFile(File file) {
        if (file.isDirectory()) {
            for (File x : file.listFiles()) {
                if (x.isDirectory()) {
                    searchFile(x);
                }
                if (x.isFile()) {
                    if (x.getName().contains(".")) {
                        if (exts.contains(x.getName().split("\\.")[1])) {
                            synchronized (queueFile) {
                                queueFile.add(x);
                            }
                        }
                    }
                }
            }
        }
    }

    public List<String> result() {
        return resault;
    }

    public Thread threadSearchFile = new Thread() {
        @Override
        public void run() {
            searchFile(new File(root));
        }
    };

    public Thread threadSearchText = new Thread() {
        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            searchText(text);
        }
    };

    public static void main(String[] args) {
        ParallerSearch ps = new ParallerSearch("D:/cod", "privet", Arrays.asList("log", "txt"));
        ps.threadSearchFile.start();
        ps.threadSearchText.start();
        try {
            ps.threadSearchText.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (String x : ps.result()) {
            System.out.println(x);
        }

    }
}
