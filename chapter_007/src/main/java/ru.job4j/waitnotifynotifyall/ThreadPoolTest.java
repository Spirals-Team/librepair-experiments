package ru.job4j.waitnotifynotifyall;

public class ThreadPoolTest {

    public static void main(String[] args) {

        Threadpool threadpool = new Threadpool(Runtime.getRuntime().availableProcessors()); //WORK ON BUGS//Threadpool.getInstance();

        for (int i = 0; i < 50; i++) {

            final int a = i;

            Counter2 counter2 = new Counter2(a);

//            simpleThreadpool.execute(counter2);
            threadpool.add(new Work(counter2));

        }

        threadpool.stop();
    }
}

final class Counter2 implements Runnable {

    private double a;


    Counter2(double a) {
        this.a = a;
    }


    @Override
    public void run() {

        double aAtTheBegin = a;

        System.out.println(Thread.currentThread().getName() + " begin JOB" + " aAtTheBegin = " + a);

        for (int i = 0; i < 1000000; i++) {
            a = a + Math.tan(a);
        }

        System.out.println(Thread.currentThread().getName() + " JOB WELL DONE!" + " aAtTheBegin = " + aAtTheBegin + " in the End - a = " + a);

    }

}
