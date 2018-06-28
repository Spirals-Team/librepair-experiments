package ru.job4j;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
 
 public class Build {
	private int heigth;
	private int weigth;
	
	/**
	* Метод сэттер для heigth.
	* @param  heigth - высота здания.
	*/ 
	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}
	
	/**
	* Метод гэттер для heigth.
	* @return heigth - высота здания.
	*/ 
	public int getHeigth() {
		return this.heigth;
	}
	
	/**
	* Метод сэттер для weigth.
	* @param  weigth - ширина здания.
	*/ 
	public void setWeigth(int weigth) {
		this.weigth = weigth;
	}
	
	/**
	* Метод гэттер для weigth.
	* @return weigth - ширина здания.
	*/ 
	public int getWeigth() {
		return this.weigth;
	}
}