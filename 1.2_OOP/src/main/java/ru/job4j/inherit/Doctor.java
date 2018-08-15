package ru.job4j.inherit;
/**
*Класс-наследник.
*@author ifedorenko
*@since 22.08.2017
*@version 1
*/
public class Doctor extends Profession {
	/**
	*Конструктор.
	*@param name Задаем имя при создании объекта
	*/
    public Doctor(String name) {
        super(name);
    }
	/**
	*Основной метод в программе.
	*@param patient указываем кто находится на приеме.
	*@return Кто с кем
	*/
	public String heal(Patient patient) {
        String work = "Доктор " + this.getName() + " лечит " + patient.getName();
		return work;
	}
}