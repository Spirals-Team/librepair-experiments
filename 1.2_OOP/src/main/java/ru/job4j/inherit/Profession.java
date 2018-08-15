package ru.job4j.inherit;
/**
*Класс-родитель.
*@author ifedorenko
*@since 22.08.2017
*@version 1
*/
public class Profession {
	/**
	*@param experience опыт работы
	*/
	private int experience;
	/**
	*@param name имя
	*/
	private String name;
	/**
	*@param speciality специальность
	*/
	private String speciality;
	/**
	*Конструктор по-умолчанию.
	*/
	public Profession() {
	}
	/**
	*Конструктор.
	*@param speciality указываем специальность
	*@param experience указываем опыт работы
	*/
	public Profession(int experience, String speciality) {
		this.speciality = speciality;
		this.experience = experience;
	}
	/**
	 *Конструктор.
	 *@param name Задаем имя при создании объекта
	 */
	public Profession(String name) {
		this.name = name;
	}
	/**
	*@return имя
	*/
	public String getName() {
		return this.name;
	}
	/**
	*@return специальность
	*/
	public String getSpeciality() {
		return this.speciality;
	}
	/**
	*@return опыт
	*/
	public int getExperience() {
		return this.experience;
	}
}