package ru.skorikov;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 21.01.18
 * @ version: java_kurs_standart
 * @param <T> тип данных.
 */
@ThreadSafe
public class BlockingQueue<T> {
    /**
     * WorkList.
     */
    @GuardedBy("this")
    private LinkedList<T> queue;
    /**
     * Размер очереди.
     */
    @GuardedBy("this")
    private int limit;

    /**
     * Конструктор.
     *
     * @param limit размер.
     */
    BlockingQueue(int limit) {
        this.limit = limit;
        this.queue = new LinkedList();
    }

    /**
     * Метод добавляет в очередь.
     *
     * @param item то что добавляем.
     * @throws InterruptedException исключение.
     */
    public synchronized void add(T item) throws InterruptedException {
        //Пока лист заполнен (list.size == limit) добавлять не можем.
        while (this.queue.size() == this.limit) {
            //спим
            wait();
        }
        //можем добавить в конец листа.
        this.queue.add(item);
        //пробудить всех
        this.notifyAll();
    }

    /**
     * Метод получает из очереди.
     *
     * @return то что получаем.
     * @throws InterruptedException исключение.
     */
    public synchronized Object take() throws InterruptedException {
        //пока брать нечего
        while (this.queue.size() == 0) {
            //спим
            wait();
        }
        //получаем первое что-то из листа
        Object rezult = this.queue.get(0);
        //удаляем первый элемент - освобождаем 1 элемент в листе
        this.queue.remove(0);
        //будим всех
        this.notifyAll();
        //возвращаем данные.
        return rezult;
    }
}
