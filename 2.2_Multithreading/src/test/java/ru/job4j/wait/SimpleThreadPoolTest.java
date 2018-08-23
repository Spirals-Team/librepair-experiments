package ru.job4j.wait;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleThreadPoolTest {
    /**
     * @param stp Object SimpleThreadPool
     */
    private SimpleThreadPool stp;
    /**
     * @param task for test
     */
    private Runnable task = new Runnable() {
        @Override
        public void run() {
                try {
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName() + " is working");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

    /**
     * Method for testing №1.
     * @throws Exception exception
     */
    @Test
    public void whenAddTaskInPoolAndCompletedThisTaskThenReturnSizeofQueueIsNull() throws Exception {
        stp = new SimpleThreadPool();
        stp.start();
        stp.add(task);
        stp.shutdown();
        Thread.currentThread().join(100);
        int result = stp.getTasks().size();
        assertThat(result, is(0));
    }

    /**
     * Method for testing №2.
     */
    @Test
    public void whenTrustAddNewTaskInStoppedPoolThenReturnError() {
        stp = new SimpleThreadPool();
        stp.start();
        stp.shutdown();
        try {
            stp.add(task);
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
}