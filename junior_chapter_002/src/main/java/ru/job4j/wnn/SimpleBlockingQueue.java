package ru.job4j.wnn;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 15.03.2018.
 * @version 1.0.
 * @since 0.1.
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    public void offer(T value) {
    }

    public T peek() {
        return null;
    }
}
