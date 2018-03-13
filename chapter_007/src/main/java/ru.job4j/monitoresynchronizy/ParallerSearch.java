package ru.job4j.monitoresynchronizy;


import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

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

//возникла проблема с остановкой consumer - удалось решить:
// КОНЕЦ!!!
//                    Exception in thread "Thread-0" java.lang.IllegalMonitorStateException
//            at java.lang.Object.notifyAll(Native Method)
//            at ru.job4j.monitoresynchronizy.ParallerSearch$TaskFindBlockingQueueProducer.run(ParallerSearch.java:275)
//            at java.lang.Thread.run(Thread.java:748)
//            threadConsumer.isAlive()
//http://anandsekar.github.io/cancel-support-for-threads/
//        http://qaru.site/questions/21276/how-do-you-kill-a-thread-in-java
//queue.put("stopnow");
public class ParallerSearch {

    File root;
    String text;
    ArrayList<String> exts;

    public ParallerSearch(File root, String text, ArrayList<String> exts) {
        this.root = root;
        this.text = text;
        this.exts = exts;
    }

    public static void main(String[] args) throws InterruptedException {

        long startTime = System.currentTimeMillis();

        File[] disks = File.listRoots();

        int numberOfDisks = disks.length;

        if (numberOfDisks > 0) {

            ArrayList<String> exts = new ArrayList<>();

            exts.add("txt");

            BlockingQueue blockingQueue = new BlockingQueue(1);
//            LinkedBlockingQueue blockingQueue = new LinkedBlockingQueue(1);
//            SynchronousQueue blockingQueue = new SynchronousQueue<>();

            Thread threadProducer = new Thread(new TaskFindBlockingQueueProducer(blockingQueue, "asdfasdfasdf", disks[0], exts));

            threadProducer.start();

            Thread threadConsumer = new Thread(new Consumer(blockingQueue));

            threadConsumer.start();

            threadProducer.join();

            long endTime = System.currentTimeMillis();

            Thread.sleep(1000);

            if (threadConsumer.isAlive()) {
                System.out.println("threadConsumer.isAlive()");
            }

            if (threadProducer.isAlive()) {
                System.out.println("threadProducer.isAlive()");
            }

//            threadConsumer.interrupt();

            System.out.println("threadConsumer.getState() = " + threadConsumer.getState());

            long timeConsumedMillis = endTime - startTime;

            System.out.println("all time: " + timeConsumedMillis + "ms");
        }
    }

    static class Filter2 implements FileFilter {
        ArrayList<String> ext;

        Filter2(ArrayList<String> ext) {
            this.ext = ext;
        }

        private String getExtension(File pathname) {
            String filename = pathname.getPath();
            int i = filename.lastIndexOf('.');
            if ((i > 0) && (i < filename.length() - 1)) {
                return filename.substring(i + 1).toLowerCase();
            }
            return "";
        }

        public boolean accept(File pathname) {

            if (!pathname.isFile()) {
                return true;
            }

            String extension = getExtension(pathname);

            for (String e : ext) {
                if (e.equalsIgnoreCase(extension)) {
                    return true;
                }
            }
            return false;
        }
    }

    static class Consumer implements Runnable {

        private final BlockingQueue queue;
//        private final LinkedBlockingQueue queue;
//        private final SynchronousQueue queue;

        public static final String POISON_PILL  = "stopnow";

        Consumer(BlockingQueue queue) {
//        Consumer(LinkedBlockingQueue queue) {
//        Consumer(SynchronousQueue queue) {

            this.queue = queue;
        }

        @Override
        public void run() {

            boolean stop = false;

//                while (true) {
                while (!stop) {

                System.out.println("Consumer run!!!!");

                try {
                    System.out.println("!jobDone");
//                    consume();
                    String findsFile = (String) queue.take();

                    if (POISON_PILL.equals(findsFile)) {

                        System.out.println("STOP!!!!!!!!!");

                        stop = true;

                    } else {

                        System.out.println("Consumer takes founds file from queue!!!!!!!!!: " + findsFile);

                    }

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }
            }
        }

//        private void consume() throws InterruptedException {
//            String findsFile = (String ) queue.take();
//            System.out.println("Consumer takes founds file from queue!!!!!!!!!: " + findsFile);
//        }

    }


    public static class TaskFindBlockingQueueProducer implements Runnable {

        private final BlockingQueue queue;
//        private final LinkedBlockingQueue queue;
//        private final SynchronousQueue queue;

        String findTxt;

        File szDir;

        ArrayList<String> exts;

        public TaskFindBlockingQueueProducer(BlockingQueue queue, String findTxt, File szDir, ArrayList<String> exts) {
//        public TaskFindBlockingQueueProducer(LinkedBlockingQueue queue, String findTxt, File szDir, ArrayList<String> exts) {
//        public TaskFindBlockingQueueProducer(SynchronousQueue queue, String findTxt, File szDir, ArrayList<String> exts) {


            this.queue = queue;

            this.findTxt = findTxt;

            this.szDir = szDir;

            this.exts = exts;
        }

        private void produce22(File szName) throws InterruptedException {

            System.out.println("TaskFindBlockingQueueProducer: " + szName);

            String s;

            try {

                LineNumberReader lnr = new LineNumberReader(new BufferedReader(new FileReader(szName)));

                while (true) {

                    s = lnr.readLine();

                    if (s == null) {
                        break;
                    }

                    if (s.indexOf(findTxt) != -1) {

                        System.out.println("TaskFindBlockingQueueProducer  FIND TEXT and put to queue!!!!!: " + szName);

                        queue.put(szName.getPath());

                        break;

                    }
                }

                lnr.close();

            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        void produce(File szDir, ArrayList<String> exts) throws InterruptedException {

            File f = szDir; //new File(szDir);
            File[] sDirList = f.listFiles(new Filter2(exts)); //f.list();

            // Если папка пуста то не заходим
            if (sDirList != null) {

                for (int i = 0; i < sDirList.length; i++) {

                    File currFileOrDir = sDirList[i];

                    if (currFileOrDir.isFile()) {

                        System.out.println("name: " + Thread.currentThread().getName() + " currFileOrDir: " + currFileOrDir);

                        produce22(currFileOrDir);

                    } else {

                        produce(currFileOrDir, exts);

                    }
                }
            }
        }

        public void run() {

            System.out.println("Producer run");
            System.out.println("name: " + Thread.currentThread().getName());

                try {

                    produce(szDir, exts);



                } catch (InterruptedException e) {

                    e.printStackTrace();

                }

            System.out.println("КОНЕЦ Producer!!!");

            try {
                queue.put("stopnow");
                System.out.println("Producer says: stopnow!!!!!!!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class BlockingQueue<T> {

    private List queue = new LinkedList();

    private int limit = 10;

    public BlockingQueue(int limit) {

        this.limit = limit;
    }

    public int getSize() {
        return queue.size();
    }

    public synchronized void put(T item) throws InterruptedException {

        System.out.println("BlockingQueue try to put: " + item + " (method put)");

        while (this.queue.size() == this.limit) {

            System.out.println("BlockingQueue queue is full, waiting until space is free (method put)");

            wait();
        }

        if (this.queue.size() == 0) {

            System.out.println("BlockingQueue queue is empty, notifyAll (method put)");

            notifyAll();
        }

        this.queue.add(item);

        System.out.println("BlockingQueue put ok: " + item + " (method put)");

    }

    public synchronized T take() throws InterruptedException {

        System.out.println("BlockingQueue try to take (method take)");

        while (this.queue.size() == 0) {

            System.out.println("BlockingQueue queue is empty, waiting until smth is put (method take)");

            wait();
        }

        if (this.queue.size() == this.limit) {

            System.out.println("BlockingQueue queue is full, notify (method take)");

            notifyAll();
        }

        T item = (T) this.queue.remove(0);

        System.out.println("BlockingQueue take ok: " + item + "method take");

        return item;
    }
}