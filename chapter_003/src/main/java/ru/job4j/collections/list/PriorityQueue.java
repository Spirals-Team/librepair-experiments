package ru.job4j.collections.list;

import java.util.Comparator;
import java.util.LinkedList;

public class PriorityQueue {
    private LinkedList<Task> tasks = new LinkedList<>();

    private void add(int priority, Task value) {
        tasks.add(value);
        tasks.sort(new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return Integer.compare(o1.getPriority(), o2.getPriority());
            }
        });
    }

    public void put(Task task) {
        add(task.getPriority(), task);
    }

    public Task take() {
        return this.tasks.poll();
    }
}
