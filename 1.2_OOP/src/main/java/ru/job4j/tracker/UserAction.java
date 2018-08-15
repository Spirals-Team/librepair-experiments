package ru.job4j.tracker;
/**
 *Интерфейс UserAction.
 *@author ifedorenko
 *@since 26.08.2017
 *@version 1
 */
public interface UserAction {
	/**
	 * Метод возвращает ключ опции.
	 * @return ключ
	 */
	int key();
	/**
	 * Основной метод.
	 * @param input объект типа Input
	 * @param tracker объект типа Tracker
	 */
	void execute(Input input, Tracker tracker);
	/**
	 * Метод возвращает информацию о данном пункте меню.
	 * @return Строка меню
	 */
	String info();
}