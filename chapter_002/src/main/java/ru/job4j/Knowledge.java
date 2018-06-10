package ru.job4j;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
 
 public class Knowledge {
	private String science;
	private String course;
	
	/**
	* Метод сэттер для science.
	* @param  science - изучаемая дисциплина.
	*/ 
	public void setScience(String science) {
		this.science = science;
	}
	
	/**
	* Метод гэттер для science.
	* @return science - изучаемая дисциплина.
	*/ 
	public String getScience() {
		return this.science;
	}
	
	/**
	* Метод сэттер для course.
	* @param  course - курс.
	*/ 
	public void setСourse(String course) {
		this.course = course;
	}
	
	/**
	* Метод гэттер для course.
	* @return course - курс.
	*/ 
	public String getСourse() {
		return this.course;
	}
}	