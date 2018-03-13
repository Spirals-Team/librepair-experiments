package ru.job4j.monitoresynchronizy.examples;




        import java.io.*;
        import java.util.ArrayList;
        import java.util.LinkedList;
        import java.util.List;
        import java.util.Random;
        import java.util.concurrent.*;

//public class ParallerSearch(String root, String text, List<String> exts) {
//        }
//
//        ,где
//        root - путь до папки откуда надо осуществлять поиск.
//        text - заданных текст.
//        exts - расширения файлов в которых нужно делать поиск.
//
//        Приложения должно искать текст в файлах (в том числе и в подкаталогах) и сохранять адрес файла.
//
//        List<String> result(); - возвращает список всех файлов.
//
//        Логика приложения.
//
//        1. Запустить код.
//        2. Внутри запустить несколько потоков. Объяснить для чего нужно делать потоки.
//        3. Дождаться завершения поиска.результат.
//        4. Вывести на консоль результат.
// WORK ON BUGS
//        1.  private volatile List<String> result = new ArrayList<>();  -
// это не обеспечить безопастность.
//        2. да ты верно говоришь. оно работает медленно из-за того. что hhd диски
// работает однопоточно. он головку может только в одно место поместить разово.
//        3. цель этого задания. один поток считывает данные и засовывает их в список.
// а другой достает их. тут лушче всего использовать блокирующую очередь.
//        поправь. убери пул. тут он не нужен.
//public class MyParallelSearchWithAllMyVersion {
//
//    File root;
//    String text;
//    ArrayList<String> exts;
//
//    private volatile List<String> result = new ArrayList<>();
//
//
//    public MyParallelSearchWithAllMyVersion(File root, String text, ArrayList<String> exts) {
//        this.root = root;
//        this.text = text;
//        this.exts = exts;
//
//    }
//
//
//    //    public static void main(String[] args) throws InterruptedException {
////
////        long startTime = System.currentTimeMillis();
////
////        File[] disks = File.listRoots();
////
////        int numberOfDisks = disks.length;
////
////        if (numberOfDisks > 0) {
////
////            ArrayList<String> exts = new ArrayList<>();
////
////            exts.add("txt");
////
////            ParallerSearch parallerSearch = new ParallerSearch(disks[0], "asdfasdfasdf", exts);
////
////            List<String> result = parallerSearch.result(100);
////
////            for (String path : result) {
////                System.out.println("Found path: " + path);
////            }
////
////            long endTime = System.currentTimeMillis();
////
////            long timeConsumedMillis = endTime - startTime;
////
////            System.out.println("all time: " + timeConsumedMillis + "ms");
////
////        }
////    }
//    public static void main(String[] args) throws InterruptedException {
//
//        long startTime = System.currentTimeMillis();
//
//        File[] disks = File.listRoots();
//
//        int numberOfDisks = disks.length;
//
//        if (numberOfDisks > 0) {
//
//            ArrayList<String> exts = new ArrayList<>();
//
//            exts.add("txt");
//
////            List<String> result = new ArrayList<String>();
//
////            LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(1);
//
//            BlockingQueue blockingQueue = new BlockingQueue(1);
//
//            Thread threadProducer = new Thread(new TaskFindBlockingQueueProducer(blockingQueue, "asdfasdfasdf", disks[0], exts));
//
//            threadProducer.start();
//
////            Thread.currentThread().sleep(1000);
//
//            Thread threadCustomer = new Thread(new Consumer(blockingQueue));
//
//            threadCustomer.start();
//
//            threadProducer.join();
//            threadCustomer.join();
//
////            for (String path : result) {
////                System.out.println("Found path: " + path);
////            }
//
//            long endTime = System.currentTimeMillis();
//
//            long timeConsumedMillis = endTime - startTime;
//
//            System.out.println("all time: " + timeConsumedMillis + "ms");
//        }
//    }
//
//
//    public List<String> result(int numberOfThreads) {
//
//        ExecutorService pool = Executors.newFixedThreadPool(numberOfThreads);
//
//        list(root, pool, result, exts);
//
//        return result;
//    }
//
//    void list(File szDir, ExecutorService pool, List<String> result, ArrayList<String> exts) {
//
//        File f = szDir; //new File(szDir);
//        File[] sDirList = f.listFiles(new Filter(exts)); //f.list();
//
//        // Если папка пуста то не заходим
//        if (sDirList != null) {
//            for (int i = 0; i < sDirList.length; i++) {
//                File currFileOrDir = sDirList[i];
//                if (currFileOrDir.isFile()) {
//                    pool.submit(new TaskFind(text, currFileOrDir.getPath()));
////                    new TaskFindNotThread(text, currFileOrDir.getPath()).run();
//                } else {
//                    list(currFileOrDir, pool, result, exts);
//                }
//            }
//        }
//    }
//
//
//    class Filter implements FileFilter {
//        ArrayList<String> ext;
//
//        Filter(ArrayList<String> ext) {
//            this.ext = ext;
//        }
//
//        private String getExtension(File pathname) {
//            String filename = pathname.getPath();
//            int i = filename.lastIndexOf('.');
//            if ((i > 0) && (i < filename.length() - 1)) {
//                return filename.substring(i + 1).toLowerCase();
//            }
//            return "";
//        }
//
//        public boolean accept(File pathname) {
//
//            if (!pathname.isFile()) {
//                return true;
//            }
//
//            String extension = getExtension(pathname);
//
//            for (String e : ext) {
//                if (e.equalsIgnoreCase(extension)) {
//                    return true;
//                }
//            }
//            return false;
//        }
//    }
//
//    static class Filter2 implements FileFilter {
//        ArrayList<String> ext;
//
//        Filter2(ArrayList<String> ext) {
//            this.ext = ext;
//        }
//
//        private String getExtension(File pathname) {
//            String filename = pathname.getPath();
//            int i = filename.lastIndexOf('.');
//            if ((i > 0) && (i < filename.length() - 1)) {
//                return filename.substring(i + 1).toLowerCase();
//            }
//            return "";
//        }
//
//        public boolean accept(File pathname) {
//
//            if (!pathname.isFile()) {
//                return true;
//            }
//
//            String extension = getExtension(pathname);
//
//            for (String e : ext) {
//                if (e.equalsIgnoreCase(extension)) {
//                    return true;
//                }
//            }
//            return false;
//        }
//    }
//
//    public class TaskFind implements Runnable {
//
//        String szName, findTxt;
//
//        public TaskFind(String findTxt, String szName) {
//            this.szName = szName;
//            this.findTxt = findTxt;
//        }
//
//        public void run() {
//
//            System.out.println("name: " + Thread.currentThread().getName() + " file: " + szName);
//
//            String s;
//
//            try {
//
//                LineNumberReader lnr = new LineNumberReader(new BufferedReader(new FileReader(szName)));
//
//                while (true) {
//
//                    s = lnr.readLine();
//
//                    if (s == null) {
//                        break;
//                    }
//
//                    if (s.indexOf(findTxt) != -1) {
//
//                        System.out.println("FIND TEXT!!!!!: " + szName);
//
//                        result.add(szName);
//
//                        break;
//
//                    }
//                }
//
//                lnr.close();
//
//            } catch (Exception e) {
//                System.out.println(e.toString());
//            }
//        }
//    }
//
//    static class Producer implements Runnable {
//
//        private final BlockingQueue queue;
//
//        File szDir;
//
//        ArrayList<String> exts;
//
//        Producer(BlockingQueue queue, File szDir, ArrayList<String> exts) {
//
//            this.queue = queue;
//
//            this.szDir = szDir;
//        }
//
//        @Override
//        public void run() {
//
//            System.out.println("Producer run");
//
//            while (true) {
//
//                try {
//                    queue.put(produce());
//
////                    Thread.currentThread().sleep(200);
//
//                } catch (InterruptedException e) {
//
//                    e.printStackTrace();
//                }
//            }
//
//        }
//
//        File list2(File szDir, ArrayList<String> exts) {
//
//            File f = szDir; //new File(szDir);
//            File[] sDirList = f.listFiles(new Filter2(exts)); //f.list();
//
//            // Если папка пуста то не заходим
//            if (sDirList != null) {
//                for (int i = 0; i < sDirList.length; i++) {
//                    File currFileOrDir = sDirList[i];
//                    if (currFileOrDir.isFile()) {
////                    pool.submit(new TaskFind(text, currFileOrDir.getPath()));
//                        return currFileOrDir;
//                    } else {
//                        list2(currFileOrDir, exts);
//                    }
//                }
//            }
//            return f;
//        }
//
//        private File produce() {
//
////            Integer i = new Random().nextInt(100);
////
////            System.out.println("Producer produce: " + i);
////
////            return i;
//
////            File f = szDir;
////
////            File[] sDirList = f.listFiles(new Filter2(exts));
////
////            if (sDirList != null) {
////                for (int i = 0; i < sDirList.length; i++) {
////                    File currFileOrDir = sDirList[i];
////                    if (currFileOrDir.isFile()) {
////                        pool.submit(new TaskFind(text, currFileOrDir.getPath()));
////
////                    } else {
////                        list(currFileOrDir, pool, result, exts);
////                    }
////                }
////            }
//
//            return list2(szDir, exts);
//
//
////            File f = szDir; //new File(szDir);
////            File[] sDirList = f.listFiles(new Filter(exts)); //f.list();
////
////            // Если папка пуста то не заходим
////            if (sDirList != null) {
////                for (int i = 0; i < sDirList.length; i++) {
////                    File currFileOrDir = sDirList[i];
////                    if (currFileOrDir.isFile()) {
////                        pool.submit(new TaskFind(text, currFileOrDir.getPath()));
//////                    new TaskFindNotThread(text, currFileOrDir.getPath()).run();
////                    } else {
////                        list(currFileOrDir, pool, result, exts);
////                    }
////                }
////            }
//        }
//    }
//
//    static class Consumer implements Runnable {
//
//        private final BlockingQueue queue;
//
//        Consumer(BlockingQueue queue) {
//
//            this.queue = queue;
//        }
//
//        @Override
//        public void run() {
//
//            System.out.println("Consumer run");
//
//            while (true) {
//
//                try {
//
//                    consume();
//
////                    Thread.currentThread().sleep(1500);
//
//                } catch (InterruptedException e) {
//
//                    e.printStackTrace();
//
//                }
//            }
//
//        }
//
//        private void consume() throws InterruptedException {
//
////            Integer i = (Integer) queue.take();
////
////            System.out.println("Consumer consumed: " + i);
//
//            String findsFile = (String) queue.take();
//
//            System.out.println("Consumer takes founds file from queue!!!!!!!!!: " + findsFile);
//
//        }
//
//    }
//
//
//    public static class TaskFindBlockingQueue implements Runnable {
//
//        private final BlockingQueue queue;
//
//        String findTxt;
//
//
//        List<String> result;
//
//        public TaskFindBlockingQueue(BlockingQueue queue, String findTxt, List<String> result) {
//            this.queue = queue;
//
//            this.findTxt = findTxt;
//        }
//
//        private void consume() throws InterruptedException {
//
//            String szName = (String) queue.take();
//
//            System.out.println("Consumer consumed: " + szName);
//
//            try {
//
//                LineNumberReader lnr = new LineNumberReader(new BufferedReader(new FileReader(szName)));
//
//                while (true) {
//
//                    String s;
//
//                    s = lnr.readLine();
//
//                    if (s == null) {
//                        break;
//                    }
//
//                    if (s.indexOf(findTxt) != -1) {
//
//                        System.out.println("FIND TEXT!!!!!: " + szName);
//
//                        result.add(szName);
//
//                        break;
//
//                    }
//                }
//
//                lnr.close();
//
//            } catch (Exception e) {
//                System.out.println(e.toString());
//            }
//
//
//        }
//
//        public void run() {
//
//            System.out.println("Consumer run");
//            System.out.println("name: " + Thread.currentThread().getName());
//
//            while (true) {
//
//                try {
//
//                    consume();
//
//                } catch (InterruptedException e) {
//
//                    e.printStackTrace();
//
//                }
//            }
//        }
//
//
//    }
//
//    public static class TaskFindBlockingQueueProducer implements Runnable {
//
//        private final BlockingQueue queue;
//
//        String findTxt;
//
//        File szDir;
//
//        ArrayList<String> exts;
//
//
////        List<String> result;
//
//        public TaskFindBlockingQueueProducer(BlockingQueue queue, String findTxt, File szDir, ArrayList<String> exts) {
//
//            this.queue = queue;
//
//            this.findTxt = findTxt;
//
//            this.szDir = szDir;
//
//            this.exts = exts;
//        }
//
//        private void produce22(File szName) throws InterruptedException {
//
////            String szName = (String) queue.take();
//
//            System.out.println("TaskFindBlockingQueueProducer: " + szName);
//
//            String s;
//
//            try {
//
//                LineNumberReader lnr = new LineNumberReader(new BufferedReader(new FileReader(szName)));
//
//                while (true) {
//
//
//
//                    s = lnr.readLine();
//
//                    if (s == null) {
//                        break;
//                    }
//
//                    if (s.indexOf(findTxt) != -1) {
//
//                        System.out.println("TaskFindBlockingQueueProducer  FIND TEXT and put to queue!!!!!: " + szName);
//
////                        result.add(szName);
//                        queue.put(szName.getPath());
//
//                        break;
//
//                    }
//                }
//
//                lnr.close();
//
//            } catch (Exception e) {
//                System.out.println(e.toString());
//            }
//
//
//        }
//
//        void produce(File szDir, ArrayList<String> exts) throws InterruptedException {
//
//            File f = szDir; //new File(szDir);
//            File[] sDirList = f.listFiles(new Filter2(exts)); //f.list();
//
//            // Если папка пуста то не заходим
//            if (sDirList != null) {
//                for (int i = 0; i < sDirList.length; i++) {
//                    File currFileOrDir = sDirList[i];
//                    if (currFileOrDir.isFile()) {
////                    pool.submit(new TaskFind(text, currFileOrDir.getPath()));
//                        System.out.println("name: " + Thread.currentThread().getName() + " currFileOrDir: " + currFileOrDir);
//                        produce22(currFileOrDir);
//                    } else {
//                        produce(currFileOrDir, exts);
//                    }
//                }
//            }
////            return f;
//        }
//
//        public void run() {
//
//            System.out.println("Consumer run");
//            System.out.println("name: " + Thread.currentThread().getName());
//
////            while (true) {
//
//            try {
//
//                produce(szDir, exts);
//
//            } catch (InterruptedException e) {
//
//                e.printStackTrace();
//
////                }
//
//            }
//
//            System.out.println("КОНЕЦ!!!");
//        }
//
//
//    }
//
//
//    public class TaskFindNotThread {
//
//        String szName, findTxt;
//
//        public TaskFindNotThread(String findTxt, String szName) {
//            this.szName = szName;
//            this.findTxt = findTxt;
//        }
//
//        public void run() {
//
//            System.out.println("name: " + Thread.currentThread().getName() + " file: " + szName);
//
//            String s;
//
//            try {
//                LineNumberReader lnr = new LineNumberReader(new BufferedReader(new FileReader(szName)));
//                while (true) {
//
//                    s = lnr.readLine();
//
//                    if (s == null) {
//                        break;
//                    }
//
//                    if (s.indexOf(findTxt) != -1) {
//
//                        System.out.println("FIND TEXT!!!!!: " + szName);
//
//                        result.add(szName);
//
//                        break;
//                    }
//                }
//
//                lnr.close();
//
//            } catch (Exception e) {
//                System.out.println(e.toString());
//            }
//        }
//    }
//}
//
//
//class BlockingQueue<T> {
//
//    private List queue = new LinkedList();
//
//    private int limit = 10;
//
//    public BlockingQueue(int limit) {
//
//        this.limit = limit;
//    }
//
//    public synchronized void put(T item) throws InterruptedException {
//
//        System.out.println("BlockingQueue try to put: " + item + " (method put)");
//
//        while (this.queue.size() == this.limit) {
//
//            System.out.println("BlockingQueue queue is full, waiting until space is free (method put)");
//
//            wait();
//        }
//
//        if (this.queue.size() == 0) {
//
//            System.out.println("BlockingQueue queue is empty, notifyAll (method put)");
//
//            notifyAll();
//        }
//
//        this.queue.add(item);
//
//        System.out.println("BlockingQueue put ok: " + item + " (method put)");
//
//    }
//
//    public synchronized T take() throws InterruptedException {
//
//        System.out.println("BlockingQueue try to take (method take)");
//
//        while (this.queue.size() == 0) {
//
//            System.out.println("BlockingQueue queue is empty, waiting until smth is put (method take)");
//
//            wait();
//        }
//
//        if (this.queue.size() == this.limit) {
//
//            System.out.println("BlockingQueue queue is full, notify (method take)");
//
//            notifyAll();
//        }
//
//        T item = (T) this.queue.remove(0);
//
//        System.out.println("BlockingQueue take ok: " + item + "method take");
//
//        return item;
//    }
//
//
//
//
//}
