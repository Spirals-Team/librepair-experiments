package ru.job4j;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
 
 public class Engineer extends Profession {
	/**
	* Метод вернуть здание.
	* @return здание.
	*/  
	public Build build() {
		return new Build();
	}
}	