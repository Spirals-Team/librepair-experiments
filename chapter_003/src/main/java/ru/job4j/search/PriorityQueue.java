package ru.job4j.search;

import java.util.LinkedList;

public class PriorityQueue {
    private LinkedList<Task> tasks = new LinkedList<>();

    /**
     * Метод должен вставлять в нужную позицию элемент.
     * Позиция определять по полю приоритет.
     * Для вставик использовать add(int index, E value)
     *
     * @param task задача
     */

    public void put(Task task) {
        if (this.tasks.size() == 0 || this.tasks.getLast().getPrioriti() < task.getPrioriti()) {
            this.tasks.add(task);
        } else {
            this.tasks.add(this.tasks.size() - 1, task);
        }
    }

    public Task take() {
        return this.tasks.poll();
    }
}
