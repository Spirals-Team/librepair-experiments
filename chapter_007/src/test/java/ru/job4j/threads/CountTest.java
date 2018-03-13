package ru.job4j.threads;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CountTest {

    @Test
    public void test() {

        System.out.println("start");

        String text = Count.getText();

        new Thread(new Count.CountTheNumberOfWords(text)).start();

        new Thread(new Count.CountTheNumberOfSpaces(text)).start();

        System.out.println("finish");
    }

    @Test
    public void testThreadsForWaiting() throws InterruptedException {

        String text = Count.getText();

        Thread thread1 = new Thread(new Count.ShowStart());
        Thread thread2 = new Thread(new Count.CountTheNumberOfWords(text));
        Thread thread3 = new Thread(new Count.CountTheNumberOfSpaces(text));
        Thread thread4 = new Thread(new Count.ShowFinish());

        thread1.start();

        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread2.start();

        thread3.start();

        try {
            thread3.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread4.start();

        try {
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    //    1. В программе необходимо реализовать механизм остановки нити.
//2. В программе должно быть две нити. Одна нить проверять общее время работы программы.
//    Если время работы программы больше заданного времени, необходимо остановить выполнение программы.
//            3. Вторая нить - это программа для подсчета символов в тексте.
//
//            Помните, что метод Thread.interrupt() - выставлять флаг, но не останавливает нить.
//
//    public class Time impl Runnable
//
//    public class CountChar impl Runnable
    @Test
    public void testThreadsForWaitingAndStoppingAtDelay() throws InterruptedException {

        String text = Count.getText();

        Thread threadCont = new Thread(new Count.CountChar(text));

        Thread threadCurrTime = new Thread(new Count.Time(1));

        long startTime = System.currentTimeMillis();

        threadCurrTime.start();

        threadCont.start();

        while (threadCont.isAlive() && threadCurrTime.isAlive()) {
            try {
                Thread.sleep(1L); // выключаем поток main, чтобы в threadCont.run() что-то успело посчитаться
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        threadCont.interrupt();

        long endTime = System.currentTimeMillis();

        long timeConsumedMillis = endTime - startTime;

        System.out.println("all time: " + timeConsumedMillis + "ms");
    }
}