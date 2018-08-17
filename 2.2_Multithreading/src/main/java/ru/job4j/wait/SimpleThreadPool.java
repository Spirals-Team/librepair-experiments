package ru.job4j.wait;


import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;


public class SimpleThreadPool {
    private final int threadCount;
    private final SimpleBlockingQueue<Runnable> tasks;
    private volatile boolean poolShutDownInitiated = false;

    /**
     * Constructor.
     */
    public SimpleThreadPool() {
        this.tasks = new SimpleBlockingQueue<Runnable>();
        this.threadCount = Runtime.getRuntime().availableProcessors();
    }

    /**
     * Method for create ans start all threads.
     */
    public void start() {
        for (int i = 0; i < threadCount; i++) {
            ThreadForPool tfp = new ThreadForPool();
            tfp.setName("Thread - " + i);
            System.out.println("Thread - " + i + " is created");
            tfp.start();
        }
    }

    /**
     * Method add.
     *
     * @param task for adding.
     * @throws Exception exception
     */
    public void add(Runnable task) throws Exception {
        if (this.poolShutDownInitiated) {
            throw new Exception("ThreadPool has been shutDown, no further tasks can be added");
        }
        System.out.println("task has been added.");
        this.tasks.offer(task);
    }

    /**
     * Method shutdown.
     */
    public void shutdown() {
        this.poolShutDownInitiated = true;
    }

    /**
     * Getter.
     *
     * @return our queue
     */
    public SimpleBlockingQueue<Runnable> getTasks() {
        return tasks;
    }

    /**
     * Class ThreadForPool
     */
    class ThreadForPool extends Thread {
        @Override
        public void run() {
            while (true) {
                System.out.println(Thread.currentThread().getName() + " is ready to work");
                Runnable runnable = null;
                try {
                    runnable = tasks.peek();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " is executing task");
                runnable.run();
                if (poolShutDownInitiated && tasks.size() == 0) {
                    System.out.println(Thread.currentThread().getName() + " has been STOPPED.");
                    break;
                }
            }
        }
    }
}
