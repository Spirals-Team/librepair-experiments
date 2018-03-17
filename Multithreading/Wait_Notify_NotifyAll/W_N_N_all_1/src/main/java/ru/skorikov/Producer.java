package ru.skorikov;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 22.01.18
 * @ version: java_kurs_standart
 * Класс продюссер.
 */
public class Producer extends Thread {
    /**
     * Очередь в которую будем добавлять то, что произвели.
     */
    private BlockingQueue blockingQueue;
    /**
     * Удалось ли добавить в очередь.
     */
    private boolean isAdded = false;

    /**
     * Конструктор.
     *
     * @param blockingQueue очередь.
     */
    public Producer(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    /**
     * Метод который делает какую то полезную работу.
     *
     * @return то что сделал.
     */
    private Object produce() {
        //это что-то полезное.
        return Thread.currentThread().getName();
    }

    @Override
    public void run() {
        //Пробуем добавить в очередь.
        while (!isAdded) {
            //нужна проверка - что добавлили 1 раз иначе
            //забьет всю очередь
            try {
                blockingQueue.add(produce());
                isAdded = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //Возвращаем для повторного использования.
        isAdded = false;
    }
}
