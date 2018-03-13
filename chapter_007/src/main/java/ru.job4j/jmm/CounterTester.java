package ru.job4j.jmm;


//1. Проиллюстрировать проблемы с многопоточностью. [#1096]
//        Петр Арсентьев,  22.06.16 1:29
//        Создать пример иллюстрирующий проблемы, которые могут случиться при использовании многопоточности.

//два потока (или даже больше) одновременно считали значение переменной — например там было значение 99.
//        Теперь оба потока прибавляют к 99 по единице, получают оба 100 и оба записывают это значение в переменную.
//        Что там получается ? Нетрудно видеть, что будет 100. А должно быть 101

//проблема раздельного кэша. или раздельного ресурса
//Совместный ресурс для нескольких потоков
public class CounterTester {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        for (int i = 0; i < 200; i++) {
            CounterThread ct = new CounterThread(counter);
            ct.start();
        }
        Thread.sleep(1000);

        System.out.println("Counter:" + counter.getCounter());
    }
}

class Counter {
    private long counter = 0L;

    public void increaseCounter() {
        //первый способ
//    public synchronized void increaseCounter() {

        counter++;

        //второй способ
//        synchronized(this) {
//            counter++;
//        }

    }

    public long getCounter() {
        return counter;
    }
}

class CounterThread extends Thread {
    private Counter counter;

    public CounterThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            counter.increaseCounter();
        }
    }
}


