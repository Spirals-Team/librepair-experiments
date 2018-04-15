package ru.aserdyuchenko.tracker.models;

/**
 * Наследует класс Item.
 * 
 */
public class Task extends Item {
	public Task(String name, String desc) {
		this.name = name;
		this.description = desc;
	}
}
