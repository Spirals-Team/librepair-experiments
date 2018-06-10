package ru.job4j.models;

public class Task extends Item {
	
	public Task() {
		
	}
	
	//есть ли другая возможность обратится к private полям предка без создания в нем конструктора с такими параметрами?
	public Task(String name, String desc) {
		super(name, desc);
	}
}	