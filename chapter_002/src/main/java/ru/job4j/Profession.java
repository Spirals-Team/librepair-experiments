package ru.job4j;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
 
 public class Profession {
	private String name;
	private int age;
	 
	 /**
	* Метод сэттер для name.
	* @return name - имя человека.
	*/ 
	 public void setName(String name) {
		this.name = name; 
	 }
	 /**
	* Метод гэттер для name.
	* @return name - имя человека.
	*/ 
	 public String getName() {
		return this.name; 
	 }

    /**
	* Метод сэттер для age.
	* @return age - возраст человека.
	*/ 
	 public void setAge(int age) {
		this.age = age; 
	 }
	 /**
	* Метод гэттер для age.
	* @return age - возраст человека.
	*/ 
	 public int getAge() {
		return this.age; 
	 }
}	