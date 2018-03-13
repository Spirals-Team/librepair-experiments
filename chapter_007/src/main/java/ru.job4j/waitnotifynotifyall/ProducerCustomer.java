package ru.job4j.waitnotifynotifyall;

public class ProducerCustomer {

    private final Object lock = new Object();
    private boolean blockCustomer = true; // thread stack

    public void doSomething() throws InterruptedException {
        synchronized (lock) {
//            if (this.blockCustomer) { //нельзя т.к. условие меняется во время выполнения
            while (this.blockCustomer) {
                System.out.println("wait " + Thread.currentThread().getId());
                lock.wait(); // объект монитора высвобождается
                //sleep - объект монитора не высвобождается
//                Thread.sleep(11); // нельзя залипнуть может lock не освобождается.. поэтому второй не может зайти

            }
            System.out.println("usefull work " + Thread.currentThread().getId());
            //todo some usefull thing
        }
    }

    public void changeBlock(boolean enable) {
        // one more producer
        synchronized (this.lock) {
            this.blockCustomer = enable;
            System.out.println("enable " + Thread.currentThread().getId());
            this.lock.notify(); // только с потоками которые ждут лока
            //all - будет вообще все треды из состояния вэйт.
//            this.lock.notifyAll(); // обращается ко всем тредам если мало тердов то можно
        }
    }

    public static void  main(String[] args) throws InterruptedException {
        final ProducerCustomer blockingWork = new ProducerCustomer();


        //producer
        Thread producer = new Thread() {
            @Override
            public void run() {
                blockingWork.changeBlock(false);
            }
        };


        //customer
        Thread customer = new Thread() {
            @Override
            public void run() {
                try {
                    blockingWork.doSomething();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

//        Thread.sleep(10);


        producer.start();
        customer.start();



    }
}
