package ru.skorikov;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 08.01.18
 * @ version: java_kurs_standart
 * Класс увеличивает внутренний счетчик на 1 и возвращает его.
 */
@ThreadSafe
class Count {
    /**
     * Аннотация указывает на объект по которому будем синхронизироваться.
     * Синхронизация по полю value.
     */
    @GuardedBy("this")
    private int value;

    /**
     * Метод увеличивает счетчик на 1.
     */
    synchronized void increment() {
        this.value++;
    }

    /**
     * Вернуть счетчик.
     *
     * @return value
     */
    synchronized int get() {
        return this.value;
    }
}
