//package ru.job4j.monitoresynchronizy.examples;
//
//// Демонстрация приоритетов потоков.
//class clicker implements Runnable {
//    long click = 0;
//    Thread t;
//    private volatile boolean running = true;
//    public clicker(int р) {
//        t = new Thread(this);
//        t.setPriority(p);
//    }
//    public void run() {
//        while (running) {
//            click++;
//        }
//    }
//    public void stop() {
//        running = false;
//    }
//    public void start () {
//        t. start () ;
//    }
//}
//class HiLoPri {
//    public static void main(String args[]) {
//        Thread.currentThread();
//        setPriority(Thread.MAX_PRIORITY);
//        clicker hi = new clicker(Thread.NORM_PRIORITY + 2);
//        clicker lo = new clicker(Thread.NORM_PRIORITY - 2) ;
//        lo.start () ;
//        hi.start () ;
//        try {
//            Thread.sleep(10000);
//        }
//        catch (InterruptedException e) {
//            System.out.println("Главный поток прерван.");
//        }
//        lo.stop() ;
//        hi.stop();
//// Ожидание 10 секунд до прерывания.
//        try {
//            hi.t.join ();
//            lo.t.join();
//        }
//        catch (InterruptedException e) {
//            System.out.println("Перехвачено исключение InterruptedException");
//        }
//        System.out.println("Низкоприоритетный поток: " + lo.click);
//        System.out.println("Высокоприоритетный поток: " + hi.click);
//    }
//}
