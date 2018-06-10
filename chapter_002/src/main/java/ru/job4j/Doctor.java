package ru.job4j;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
 
 public class Doctor extends Profession {
	private boolean diplom;
	
	/**
	* Метод сэттер для diplom.
	* @param  diplom - наличие диплома.
	*/ 
	public void setDiplom(boolean diplom) {
		this.diplom = diplom;
	}
	
	/**
	* Метод гэттер для diplom.
	* @return diplom - наличие диплома.
	*/ 
	public boolean getDiplom() {
		return this.diplom;
	}
	
	/**
	* Метод лечащий пациента.
	* @param  man пациент типа Man.
	*/  
	public void heal(Man man) {
		
	}
	
	/**
	* Метод выписать справку для man.
	* @param  man - человек, которому выписывают справку.
	* @return справка типа Reference.
	*/  
	public Reference writeSickLeave(Man man) {
		return new Reference();
	}
}	