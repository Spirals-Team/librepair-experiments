package ru.job4j.nonblockingalgoritm;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;

public class NonBlockinngCashOnConcurrentHashMap<KEY, VALUE> {

    private final ReentrantLock lock = new ReentrantLock();

//    2. Кеш должен работать на неблокирующих алгоритмах. - использовать ConcurrentHashMap
    private final Map<KEY, VALUE> map = new ConcurrentHashMap<>();
    private final Deque<KEY> queue = new LinkedList<>();
    private final int limit;


    public NonBlockinngCashOnConcurrentHashMap(int limit) {
        this.limit = limit;
    }

//    public void put ( KEY key, VALUE value ) {
    public void add(KEY key, VALUE value) {
        VALUE oldValue = map.put(key, value);
        if (oldValue != null) {
            removeThenAddKey(key);
        } else {
            addKey(key);
        }
        if (map.size() > limit) {
            map.remove(removeLast());
        }
    }

    public VALUE computeIfPresent(KEY key, VALUE value) {

//        Использовать метод https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentHashMap.html#computeIfPresent-K-java.util.function.BiFunction-
        BiFunction<Model, Integer, Integer> biFunction = (KEY, VALUE)
                -> { VALUE = ++KEY.version; return  VALUE; };

//        нужно перед обновлением данных проверить. что текущий пользователь не затер данные другого пользователя. если
//        данные затерты то выбросить OplimisticException - такая реализация достигается за счет введение с модель
//        поля version и перед обновлением данных проверять текущую версию и ту что обновляем и увеличивать на один
//        каждый раз когда обновили. если версии не равны то кидать исключение.
        if (get(key) != value) {
            new OplimisticException("OplimisticException");
        }

        return map.computeIfPresent(key, (BiFunction<? super KEY, ? super VALUE, ? extends VALUE>) biFunction);

    }

//    3. В кеше должна быть возможность проверять валидность данных. Например. Два пользователя прочитали данные task_1
//    первый пользователь изменил поле имя и второй сделал тоже самое.
    public VALUE update(KEY key, VALUE value) {

        return computeIfPresent(key, value);

    }

    public VALUE get(KEY key) {
        removeThenAddKey(key);
        return map.get(key);
    }

    private void addKey(KEY key) {
        lock.lock();
        try {
            queue.addFirst(key);
        } finally {
            lock.unlock();
        }
    }

    private KEY removeLast() {
        lock.lock();
        try {
            final KEY removedKey = queue.removeLast();
            return removedKey;
        } finally {
            lock.unlock();
        }
    }

    private void removeThenAddKey(KEY key) {
        lock.lock();
        try {
            queue.removeFirstOccurrence(key);
            queue.addFirst(key);
        } finally {
            lock.unlock();
        }

    }

    private void removeFirstOccurrence(KEY key) {
        lock.lock();
        try {
            queue.removeFirstOccurrence(key);
        } finally {
            lock.unlock();
        }

    }


    public VALUE getSilent(KEY key) {
        return map.get(key);
    }

//    public void remove ( KEY key ) {
    public void delete(KEY key) {
        removeFirstOccurrence(key);
        map.remove(key);
    }

    public int size() {
        return map.size();
    }

    public String toString() {
        return map.toString();
    }
}