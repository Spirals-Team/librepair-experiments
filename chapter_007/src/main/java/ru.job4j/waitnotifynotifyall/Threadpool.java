package ru.job4j.waitnotifynotifyall;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

//https://habrahabr.ru/post/326146/
//https://caffinc.github.io/2016/03/simple-threadpool/
//public class MyThreadPool {
//}

//1. Инициализация пула должна быть по количеству ядер в системе.
//        2. создать метод add(Work work).
//        3. Если есть свободные треды, начать выполнение работы. если нет. то в очередь до появления свободного треда.

import java.util.Queue;
        import java.util.concurrent.ConcurrentLinkedQueue;
        import java.util.concurrent.Executor;

//public class ThreadPool {
//
//}

//WORK ON BUGS
//логика вся верная. что мне не нравиться.
//
//        1. появление статических методов. они тут не нужны
//
//        2. метод add - execute. кто-то лишний. почисти.

public class Threadpool {
        // Count of threadpools created
//WORK ON BUGS //        private static AtomicInteger poolCount = new AtomicInteger(0);
        private AtomicInteger poolCount = new AtomicInteger(0);

        // Queue of runnables
        private ConcurrentLinkedQueue<Runnable> runnables;
        // Flag to control the SimpleThreadpoolThread objects
        private AtomicBoolean execute;
        // Holds the "pool" of threads
        private List<SimpleThreadpoolThread> threads;

        /**
         * Thrown when there's a RuntimeException or InterruptedException when executing a runnable from the queue, or awaiting termination
         */
        private class ThreadpoolException extends RuntimeException {
                public ThreadpoolException(Throwable cause) {
                        super(cause);
                }
        }

        /**
         * Inner Thread class which represents the threads in the pool. It acts as a wrapper for all runnables in the queue.
         */
        private class SimpleThreadpoolThread extends Thread {
                private AtomicBoolean execute;
                private ConcurrentLinkedQueue<Runnable> runnables;

                public SimpleThreadpoolThread(String name, AtomicBoolean execute, ConcurrentLinkedQueue<Runnable> runnables) {
                        super(name);
                        this.execute = execute;
                        this.runnables = runnables;
                }

                @Override
                public void run() {
                        try {
                                // Continue to execute when the execute flag is true, or when there are runnables in the queue
                                while (execute.get() || !runnables.isEmpty()) {
                                        Runnable runnable;
                                        // Poll a runnable from the queue and execute it
                                        while ((runnable = runnables.poll()) != null) {
                                                runnable.run();
                                        }
                                        // Sleep in case there wasn't any runnable in the queue. This helps to avoid hogging the CPU.
                                        Thread.sleep(1);
                                }
                        } catch (RuntimeException | InterruptedException e) {
                                throw new ThreadpoolException(e);
                        }
                }
        }

        /**
         * Private constructor to control the creation of threadpools. Increments the poolcount whenever a new pool is created.
         *
         * @param threadCount Number of SimpleThreadpoolThreads to add to the pool
         */
//        private Threadpool(int threadCount) {
        public Threadpool(int threadCount) {
                // Increment pool count
                poolCount.incrementAndGet();
                this.runnables = new ConcurrentLinkedQueue<>();
                this.execute = new AtomicBoolean(true);
                this.threads = new ArrayList<>();
                for (int threadIndex = 0; threadIndex < threadCount; threadIndex++) {
                        SimpleThreadpoolThread thread = new SimpleThreadpoolThread("SimpleThreadpool" + poolCount.get() + "Thread" + threadIndex, this.execute, this.runnables);
                        thread.start();
                        this.threads.add(thread);
                }
        }

        /**
         * Gets a new threadpool instance with number of threads equal to the number of processors (or CPU threads) available
         *
         * @return new SimpleThreadpool
         */
//WORK ON BUGS //        public static Threadpool getInstance() {
        public Threadpool getInstance() {
                return getInstance(Runtime.getRuntime().availableProcessors());
        }

        /**
         * Gets a new threadpool instance with the number of threads specified
         *
         * @param threadCount Threads to add to the pool
         * @return new SimpleThreadpool
         */
//WORK ON BUGS //        public static Threadpool getInstance(int threadCount) {
        public Threadpool getInstance(int threadCount) {
                return new Threadpool(threadCount);
        }


        //WORK ON BUGS
//        /**
//         * Adds a runnable to the queue for processing
//         *
//         * @param runnable Runnable to be added to the pool
//         */
//        public void execute(Runnable runnable) {
//                if (this.execute.get()) {
//                        runnables.add(runnable);
//                } else {
//                        throw new IllegalStateException("Threadpool terminating, unable to execute runnable");
//                }
//        }

        public void add(Work work) {


                //WORK ON BUGS
//                execute(work);

                if (this.execute.get()) {
                        runnables.add(work);
                } else {
                        throw new IllegalStateException("Threadpool terminating, unable to execute runnable");
                }

        }

        /**
         * Awaits up to <b>timeout</b> ms the termination of the threads in the threadpool
         *
         * @param timeout Timeout in milliseconds
         * @throws TimeoutException      Thrown if the termination takes longer than the timeout
         * @throws IllegalStateException Thrown if the stop() or terminate() methods haven't been called before awaiting
         */
        public void awaitTermination(long timeout) throws TimeoutException {
                if (this.execute.get()) {
                        throw new IllegalStateException("Threadpool not terminated before awaiting termination");
                }
                long startTime = System.currentTimeMillis();
                while (System.currentTimeMillis() - startTime <= timeout) {
                        boolean flag = true;
                        for (Thread thread : threads) {
                                if (thread.isAlive()) {
                                        flag = false;
                                        break;
                                }
                        }
                        if (flag) {
                                return;
                        }
                        try {
                                Thread.sleep(1);
                        } catch (InterruptedException e) {
                                throw new ThreadpoolException(e);
                        }
                }
                throw new TimeoutException("Unable to terminate threadpool within the specified timeout (" + timeout + "ms)");
        }

        /**
         * Awaits the termination of the threads in the threadpool indefinitely
         *
         * @throws IllegalStateException Thrown if the stop() or terminate() methods haven't been called before awaiting
         */
        public void awaitTermination() throws TimeoutException {
                if (this.execute.get()) {
                        throw new IllegalStateException("Threadpool not terminated before awaiting termination");
                }
                while (true) {
                        boolean flag = true;
                        for (Thread thread : threads) {
                                if (thread.isAlive()) {
                                        flag = false;
                                        break;
                                }
                        }
                        if (flag) {
                                return;
                        }
                        try {
                                Thread.sleep(1);
                        } catch (InterruptedException e) {
                                throw new ThreadpoolException(e);
                        }
                }
        }

        /**
         * Clears the queue of runnables and stops the threadpool. Any runnables currently executing will continue to execute.
         */
        public void terminate() {
                runnables.clear();
                stop();
        }

        /**
         * Stops addition of new runnables to the threadpool and terminates the pool once all runnables in the queue are executed.
         */
        public void stop() {
                execute.set(false);
        }


}

class Work implements Runnable {

        private Runnable runnable;

        public Work(Runnable runnable) {
                this.runnable = runnable;
        }

        @Override
        public void run() {

                runnable.run();

        }
}


