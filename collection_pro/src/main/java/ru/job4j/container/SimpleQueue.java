package ru.job4j.container;

import ru.job4j.list.FirstLastList;

public class SimpleQueue<T> {
       private FirstLastList<T> queueList = new FirstLastList<>();

        public T poll() {
            return  queueList.deleteFirst();
        }

        public void push(T value) {
            queueList.add(value);
        }
}
