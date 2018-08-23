package ru.job4j.inherit;
/**
 *класс User.
 *@author ifedorenko
 *@since 22.08.2017
 *@version 1
 */
public class User {
	/**
	*@param хранит имя
	*/
	private String name;
	/**
	*Конструктор.
	*@param name Задаем имя при создании объекта
	*/
	public User(String name) {
		this.name = name;
	}
	/**
	*@return имя
	*/
	public String getName() {
		return this.name;
	}
}