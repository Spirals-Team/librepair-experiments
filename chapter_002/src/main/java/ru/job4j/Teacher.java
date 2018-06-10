package ru.job4j;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
 
 public class Teacher extends Profession {
	/**
	* Метод дает знания студенту.
	* @param  student - студент, котрому дают знания.
	* @return знание.
	*/  
	public Knowledge teach(Student student) {
		return new Knowledge();
	}
}	