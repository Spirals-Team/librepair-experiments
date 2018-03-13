package ru.job4j.monitoresynchronizy.examples;

import java.util.Arrays;
import java.util.concurrent.*;

public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException, ExecutionException {
        Callable callable = new Callable() {
            @Override
            public int[][] call() throws Exception {
                int[][] array = new int[5][];
                for (int i = 0; i < array.length; i++) {
                    array[i] = new int[]{5 * i, 5 * i + 1, 5 * i + 2, 5 * i + 3};
                }

                return array;
            }
        };

        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<int[][]> result = service.submit(callable);

        int[][] intArray = result.get();
        for (int i = 0; i < intArray.length; i++) {
            System.out.println(Arrays.toString(intArray[i]));
        }
    }
}
