package ru.job4j.waitnotifynotifyall.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MultithreadClient {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        ExecutorService threadPool = Executors.newFixedThreadPool(8);
        ThreadPool threadPool = new ThreadPool(3);
//        SimpleThreadpool threadPool = SimpleThreadpool.getInstance(3);
        Counter counter = new Counter();

        long start = System.nanoTime();

        List<Future<Double>> futures = new ArrayList<>();
        for (int i = 0; i < 400; i++) {
            System.out.println("asdf " + i);
            final int j = i;
            futures.add(
                    CompletableFuture.supplyAsync(
                            () -> counter.count(j),
                            threadPool
                    ));
        }

        double value = 0;
        for (Future<Double> future : futures) {
            value += future.get();
        }

        System.out.println(("Executed by %d s, value : " + (System.nanoTime() - start) / (1000_000_000) + " " + value));

        threadPool.shutdown();
    }
}
