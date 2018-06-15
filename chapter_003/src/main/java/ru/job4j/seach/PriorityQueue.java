package ru.job4j.seach;
import java.text.Collator;
import java.util.*;

public class PriorityQueue {
    private LinkedList<Task> tasks = new LinkedList<>();

    /**
     * Метод должен вставлять в нужную позицию элемент.
     * Позицию определять по полю приоритет.
     * Для вставок использовать add(int index, E value)
     * @param task задача
     */
    public void  put(Task task) {
        tasks.add(task);
        for (int index = 0; index < tasks.size(); index++) {
            if (task.getPriority() < tasks.get(index).getPriority()) {
                tasks.add(index, task);
                break;
            }

        }
    }

    public Task take() {
        return this.tasks.poll();
    }
}
