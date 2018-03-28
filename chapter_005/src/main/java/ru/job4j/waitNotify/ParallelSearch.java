package ru.job4j.waitNotify;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@ThreadSafe
public class ParallelSearch {
    private String root;
    private String text;
    private List<String> extensions;
    private volatile boolean finish = false;
    private DirectoryInspector inspector = new DirectoryInspector();

    @GuardedBy("this")
    private final Queue<String> files = new LinkedList<>();

    @GuardedBy("this")
    private final List<String> path = new ArrayList<>();

    public ParallelSearch(String root, String text, List<String> extensions) {
        this.root = root;
        this.text = text;
        this.extensions = extensions;
    }

    public void init() {
        Thread search = new Thread() {
            @Override
            public void run() {
                synchronized (files) {
                    inspector.walkFileTree();
                }
            }
        };
        Thread run = new Thread() {
            @Override
            public void run() {
                super.run();
            }
        };
    }

    public synchronized Queue<String> result() {
        return this.files;
    }
}
