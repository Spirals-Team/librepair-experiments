package ru.job4j.waitnotifynotifyall;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ThreadPoolTest {
    @Test
    public void whenTwoThreadsRunsThenAllTasksWillDone() {
        ThreadPool threadPool = new ThreadPool(2);
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            int j = i;
            threadPool.add(() -> {
                System.out.println(Thread.currentThread().getName() + " execute task - " + j);
                list.add(j);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPool.stop(1000);

        assertThat(list.toString(), is("[0, 1, 2, 3, 4]"));
    }
}