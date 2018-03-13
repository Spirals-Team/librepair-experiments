package ru.job4j.monitoresynchronizy.examplesp;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Count {
    @GuardedBy("this")
    private int value;

//    public void increment() {
//        this.value++;
//    }
//    Теперь давайте добавим синхронизацию и посмотрим, что скажет IDEA.
    public synchronized void increment() {
        this.value++;
    }

//    public int get() {
//        return this.value;
//    }

    public synchronized int get() {
        return this.value;
    }
}
