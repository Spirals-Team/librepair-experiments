package ru.job4j.threads;

public class AsynchOperations {

    public static final class Calculate implements Runnable {

        private final String name;

        public Calculate(String name) {
            this.name = name;
        }

        @Override
        public void run() {
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println(String.format("%s TODO asynch", this.name));

        }
    }

    public static void main(String[] args) {

        System.out.println("start");

        new Thread(new Calculate("thread 1")).start();
//        new Thread(new Calculate("thread 2")).start();
//        new Thread() {
//            @Override
//            public void run() {
////                super.run();
//                System.out.println("Thread anon TODO asynch");
//            }
//        }.start();
        System.out.println("fininsh");

    }




}
