package ru.job4j;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
 
 public class Reference {
	private int date;
	
	/**
	* Метод сэттер для date.
	* @param  date - дата оформления справки.
	*/ 
	public void setDate(int date) {
		this.date = date;
	}
	
	/**
	* Метод гэттер для date.
	* @return date - наличие диплома.
	*/ 
	public ште getDate() {
		return this.date;
	}
}