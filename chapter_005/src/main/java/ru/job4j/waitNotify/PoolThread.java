package ru.job4j.waitNotify;

public class PoolThread extends Thread {

    private final SimpleBlockingQueue taskQueue;
    private boolean       isStopped = false;

    public PoolThread(SimpleBlockingQueue taskQueue) {
        this.taskQueue = taskQueue;
    }

    public void run(){
        while(!isStopped()){
            try{
                Runnable runnable = (Runnable) taskQueue.peek();
                runnable.run();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public synchronized void doStop(){
        isStopped = true;
        this.interrupt();
    }

    public synchronized boolean isStopped(){
        return isStopped;
    }
}