package ru.job4j.search;

import java.util.LinkedList;

public class PriorityQueue {
    private LinkedList<Task> tasks = new LinkedList<>();

    /**
     * Метод должен вставляет в нужную позицию элемент.
     */
    public void put(Task task) {
        int size = this.tasks.size();
        for (int index = 0; index != size; index++) {
            if (task.getPriority() <= tasks.get(index).getPriority()) {
                this.tasks.add(index, task);
                break;
            }
        }
        this.tasks.add(task);
    }

    public Task take() {
        return this.tasks.poll();
    }
}