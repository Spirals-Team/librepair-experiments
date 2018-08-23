package ru.job4j.inherit;
/**
*Класс-наследник.
*@author ifedorenko
*@since 22.08.2017
*@version 1
*/
public class Teacher extends Profession {
	/**
	*Конструктор.
	*@param name Задаем имя при создании объекта
	*/
    public Teacher(String name) {
        super(name);
    }
	/**
	*Основной метод в программе.
	*@param student указываем кто находится на приеме.
	*@return Кто с кем
	*/
	public String teach(Student student) {
        String work = "Учитель " + this.getName() + " проводит дополнительное занятие с " + student.getName();
		return work;
	}
}