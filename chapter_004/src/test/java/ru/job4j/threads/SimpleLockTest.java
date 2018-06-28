package ru.job4j.threads;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

public class SimpleLockTest {

    class Resourse {
        int i;
        int j;
        SimpleLock simpleLock = new SimpleLock();

        void changeI() {
            simpleLock.lock();

            int i = this.i;
            if (Thread.currentThread().getName().equals("one")) {
                Thread.yield();
            }
            i++;
            this.i = i;
        }
        void changeJ() {
            int j = this.j;
            if (Thread.currentThread().getName().equals("one")) {
                Thread.yield();
            }
            j++;
            this.j = j;
            simpleLock.unlock();
        }
    }
    class MyThread extends Thread {
        Resourse resourse;
        @Override
        public void run() {
            resourse.changeI();
            resourse.changeJ();
        }
    }

    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        Resourse resourse = new Resourse();
        resourse.i = 5;
        resourse.j = 5;
        MyThread myThread = new MyThread();
        myThread.setName("one");
        MyThread myThread1 = new MyThread();
        myThread.resourse = resourse;
        myThread1.resourse = resourse;
        myThread.start();
        myThread1.start();
        myThread.join();
        myThread1.join();
        Assert.assertThat(resourse.i, is(7));
        Assert.assertThat(resourse.j, is(7));
    }
}