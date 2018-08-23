package ru.job4j.inherit;
/**
*Класс-наследник.
*@author ifedorenko
*@since 22.08.2017
*@version 1
*/
public class Engineer extends Profession {
	/**
	*Конструктор.
	*@param name Задаем имя при создании объекта
	*/
    public Engineer(String name) {
        super(name);
    }
	/**
	*Основной метод в программе.
	*@param user указываем кто находится на приеме.
	*@return Кто с кем
	*/
	public String advice(User user) {
        String work = "Специалист " + this.getName() + " проводит консультацию " + user.getName();
		return work;
	}
}