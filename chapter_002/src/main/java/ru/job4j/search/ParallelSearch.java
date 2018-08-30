package ru.job4j.search;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.Collectors;

import static java.nio.file.FileVisitResult.CONTINUE;
import static ru.job4j.search.FilenameUtils.getExtension;

/**
 * ParallelSearch, used threads to find text in files with definite extensions.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
@ThreadSafe
public class ParallelSearch {
    private final String root;
    private final String text;
    private final List<String> exts;
    private final Object lock = new Object();

    @GuardedBy("lock")
    private final Queue<String> files = new ArrayDeque<>();
    @GuardedBy("lock")
    private final LinkedList<String> paths = new LinkedList<>();
    private volatile boolean finish = false;

    public ParallelSearch(String root, String text, List<String> exts) {
        this.root = root;
        this.text = text;
        this.exts =
            exts.stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    /**
     * Init search and read.
     */
    public void init() {
        int size = Runtime.getRuntime().availableProcessors();
        createSearchThread().start();
        List<Thread> readThreads = new ArrayList<>();
        try {
            for (int i = 0; i < size; i++) {
                Thread read = createReadThread();
                read.start();
                readThreads.add(read);
                System.out.println("Start read thread: " + read.getName());
            }
            for (Thread thread : readThreads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get list of results with found paths.
     *
     * @return
     */
    public List<String> result() {
        synchronized (lock) {
            return this.paths;
        }
    }

    private Thread createSearchThread() {
        return new Thread(() -> {
            try {
                traverseDirs();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finish = true;
        });
    }

    private Thread createReadThread() {
        return new Thread(() -> {
            while (true) {
                addPathIfTextPresent();
                if (finish && isFilesEmpty()) {
                    break;
                } else if (isFilesEmpty()) {
                    try {
                        synchronized (lock) {
                            System.out.printf("%s is going to wait%n",
                                Thread.currentThread().getName());
                            System.out.println("finish :" + finish);
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void traverseDirs() throws IOException {
        Files.walkFileTree(Paths.get(root), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(final Path file,
                                             final BasicFileAttributes attrs) {
                String fileString = file.toAbsolutePath().toString();
                addFileIfExtPresent(file, fileString);
                return CONTINUE;
            }
        });
    }

    private void addPathIfTextPresent() {
        if (!isFilesEmpty()) {
            String filePath = pollFile();
            try (Scanner scanner = new Scanner(new File(filePath))) {
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    if (line.contains(text)) {
                        synchronized (lock) {
                            this.paths.add(filePath);
                        }
                        System.out.printf("%s - text found: %s%n",
                            Thread.currentThread().getName(), filePath);
                        break;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            System.out.printf("%s - text not found: %s%n",
                Thread.currentThread().getName(), filePath);
        }
    }

    private void addFileIfExtPresent(final Path file, final String fileString) {
        if (exts.contains(getExtension(fileString.toLowerCase()))) {
            System.out.printf("%s - file found at path: %s%n",
                Thread.currentThread().getName(),
                file.toAbsolutePath());
            synchronized (lock) {
                this.files.add(fileString);
                System.out.printf("%s awakes all%n",
                    Thread.currentThread().getName());
                lock.notifyAll();
            }
        }
    }

    private String pollFile() {
        synchronized (lock) {
            return this.files.poll();
        }
    }

    private boolean isFilesEmpty() {
        synchronized (lock) {
            return this.files.isEmpty();
        }
    }
}