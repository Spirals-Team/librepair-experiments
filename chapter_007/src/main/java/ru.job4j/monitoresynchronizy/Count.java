package ru.job4j.monitoresynchronizy;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;


//1. Сделать класс многопоточный счетчик Count#int incremant().
//2. Подключить библиотеку jcip-annotations
//3. В заголовке класса указать аннотацию @ThreadSafe
//4. Для поля состояния использовать аннотацию @GuardedBy
//5. В аннотации GuardedBy - указать объект монитора.
@ThreadSafe
public class Count {

    @ThreadSafe
    public static final class Counter {
        // https://samxiangyu.wordpress.com/2015/02/20/java-concurrency-guardedby/
        @GuardedBy("this")
        private long count = 0;

        public void add(long value) {
            synchronized (this) {
                this.count += value;
            }
        }
    }

    @ThreadSafe
    public static final class CounterThread extends Thread {
        private final Counter counter;

        public CounterThread(final Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            counter.add(1);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        Thread threadA = new CounterThread(counter);
        Thread threadB = new CounterThread(counter);


        threadA.start();
        threadB.start();
        threadA.join();
        threadB.join();
        System.out.println(counter.count);
    }

}


