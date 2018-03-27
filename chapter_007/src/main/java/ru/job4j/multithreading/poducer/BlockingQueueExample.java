package  ru.job4j.multithreading.poducer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Пример паттерна производителя-потребителя с использованием блокирюущей очереди.
 * @author Hincu Andrei (andreih1981@gmail.com)on 12.11.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class BlockingQueueExample {

    /**
     * Главный метод программы.
     * @param args нет.
     */
    public static void main(String[] args) {
        BlockingQueue<Message> queue = new ArrayBlockingQueue<Message>(5);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        new Thread(producer).start();
        new Thread(consumer).start();
        System.out.println("Процесс пошел.");

    }
}

/**
 * Класс производителя.
 */
class Producer implements Runnable {
    public Producer(BlockingQueue<Message> queue) {
        this.queue = queue;
    }
    private BlockingQueue<Message> queue;
    @Override
    public void run() {
        for (int i = 1; i < 20; i++) {
            Message m = new Message("" + i);
            try {
                Thread.sleep(i);
                queue.put(m);
                System.out.println("Произведено " + m.getMsg());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            Message mes = new Message("exit");
            try {
                queue.put(mes);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

    }
}

/**
 * Класс потребителя.
 */
class Consumer implements Runnable {
    private BlockingQueue<Message> queue;

    public Consumer(BlockingQueue<Message> queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
        Message msg;

        try {
            while (true) {
               msg = queue.take();
                if (msg.getMsg().equals("exit")) {
                    break;
                }
                Thread.sleep(10);
                System.out.println("Получено " + msg.getMsg());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

/**
 * Класс продукта.
 */
class Message {
    public String getMsg() {
        return msg;
    }

    public Message(String msg) {
        this.msg = msg;
    }
    private String msg;
}

