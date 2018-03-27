package ru.job4j.leambda;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

import static java.lang.Character.isDigit;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 16.11.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class Tests {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //создание листа с помощью потока.
        List<String> list = Stream.of("a", "b", "c").collect(toList());
        System.out.println(list);
        System.out.println("------------------------------");

        // распечатать лист с помощью форич
        list.forEach(st -> System.out.print(st));
        list.forEach(System.out::println);
        System.out.println("-------------------------------");
        // поднять в верхний регист и распечатать
        List<String> collection = Stream.of("a", "b", "hello")
                .map(String::toUpperCase)
                .collect(toList());
        collection.forEach(System.out::println);
        System.out.println("---------------------------------");
        // отфильтровать только слова кот начинаются с цифры.
        List<String> charsWhisDigits = Stream.of("add", "1dfg", "asd1")
                .filter(value -> isDigit(value.charAt(0)))
                .collect(toList());
        charsWhisDigits.forEach(System.out::println);
        System.out.println("-----------------------------------");

        List<Integer> arrays = Stream.of(asList(1, 2), asList(3, 4))
                .flatMap(Collection::stream).collect(toList());
        arrays.forEach(System.out::print);
        System.out.println();
        System.out.println("-----------------------------------");

        FutureTask<String> futureTask1 = new FutureTask<String>(() -> {
            TimeUnit.SECONDS.sleep(1);
            return Thread.currentThread().getName();
        });
        FutureTask<String> futureTask2 = new FutureTask<String>(() -> {
           TimeUnit.SECONDS.sleep(2);
            return Thread.currentThread().getName();
        });
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<?> future = service.submit(futureTask1);
        try {
            String s = (String) future.get();
            System.out.println(s + "Trrrr");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        service.submit(futureTask2);

        while (true) {
            try {
                if (futureTask1.isDone() && futureTask2.isDone()) {
                    System.out.println("Done");
                    service.shutdown();
                    break;
                }
                if (futureTask1.isDone()) {
                    System.out.println("future1 finish " + futureTask1.get());
                }
                System.out.println("ждем второй таск");
                String s = futureTask2.get();
                if (s != null) {
                    System.out.println("результат второго " + s);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        service.shutdown();

    }

}


