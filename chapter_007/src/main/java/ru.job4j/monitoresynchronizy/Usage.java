//package ru.job4j.monitoresynchronizy;
//
//import ru.job4j.threads.Count;
//
//public class Usage {
//
//    public static final class Counter {
//        long count = 0;
//        final Object lock = new Object();
//        static String name = "String";
//        // lock = this (инстанс объекта counter)
////        public synchronized void add(long value) { <=>  synchronized (this)
//        public void add(long value) {
////            synchronized (this) {
////            synchronized (this.lock) {
//                this.count += value;
////            }
//        }
//        // lock = Class
//        public static synchronized void echo() {
//            System.out.println(name);
//        }
//    }
//    public static final class CounterThread extends Thread {
//        protected final Counter counter; //stack
//
//        public CounterThread(final Counter counter) {
//            this.counter = counter;
//        }
//        @Override
//        public void run() {
//            // 3 способ
//            synchronized (this.counter) {
//                counter.add(1);
//            }
//        }
//
//        //если несколько объектов то код может быть таким:
//        public void sum(final Counter a, final Counter b) {
//            synchronized (a) { //a1, thread1. b1, thread2
//                synchronized (b) { // b1, thread 1. a1, thread 2 DEAD LOCK
//                    System.out.println(a.count + " " + b.count);
//                    a.add(b.count);
//                }
//            }
//        }
//    }
//
//    public static void main(String[] args) throws InterruptedException {
////        for (int i = 0; i != 10; i++) {
//        Counter counterA = new Counter(); //heap
//        Counter counterB = new Counter();
//        Thread threadA = new CounterThread(counterA); //heap
//        Thread threadB = new CounterThread(counterB); //heap
//
//        threadA.start();
//        threadB.start();
//        threadA.join();
//        threadB.join();
//        System.out.println(counterA.count); //heap
////        }
//    }
//}
