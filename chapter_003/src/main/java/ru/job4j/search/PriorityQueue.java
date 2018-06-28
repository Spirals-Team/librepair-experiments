
package ru.job4j.search;

import java.util.LinkedList;
import java.util.*;

public class PriorityQueue {
    private LinkedList<Task> tasks = new LinkedList<>();

    /**
     * Метод должен вставлять в нужную позицию элемент.
     * Позиция определять по полю приоритет.
     * Для вставик использовать add(int index, E value)
     * @param task задача
     */
    public void put(Task task) {
        //TODO добавить вставку в связанный список.
		int i = 0;
		Iterator<Task> it = tasks.iterator(); // Создает итератор для обхода линкедлиста
			if (it.hasNext()) { //если коллекция содержит еще элементы(не содержит если это первый элемент, либо уже все обошли)
				if (task.getPriority() < it.next().getPriority()) { //если приоритет вставляемой записи меньше следующей, тогда записываем запись перед следующей записью
					tasks.add(i, task);
				} else {
					i++;
				}	
			} else {
				tasks.addLast(task);
				i = 0;
			}
	}
    
	
	
    public Task take() {
        return this.tasks.poll();
    }
}