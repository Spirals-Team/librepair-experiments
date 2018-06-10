package ru.job4j;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
 
 public class Student extends Profession {
	private int course;
	
	/**
	* Метод сэттер для course.
	* @param  course - курс, на котором обучается студент.
	*/ 
	public void setCourse(int course) {
		this.course = course;
	}
	
	/**
	* Метод гэттер для course.
	* @return course - курс, на котором обучается студент.
	*/ 
	public int getCourse() {
		return this.course;
	}
	
	/**
	* Метод сна.
	*/  
	public void sleep() {
		
	}
	
	/**
	* Метод прием пищи.
	*/  
	public void eat() {
		
	}
}	