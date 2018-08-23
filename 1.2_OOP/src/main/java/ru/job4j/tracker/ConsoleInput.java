package ru.job4j.tracker;

import java.util.List;
import java.util.Scanner;
/**
 *Класс ConsoleInput.
 *@author ifedorenko
 *@since 26.08.2017
 *@version 1
 */
public class ConsoleInput implements Input {
	/**
	 * @param scanner перемення для работы с консолью
	 */
	private Scanner scanner = new Scanner(System.in);
	/**
	 * Метод ask.
	 * @param question Вопрос-подсказка для пользователя.
	 * @return введенную строку
	 */
	public String ask(String question) {
		System.out.println(question);
		return scanner.nextLine();
	}
	/**
	 * Метод ask. Перегруженный.
	 * @param question Вопрос-подсказка для пользователя.
	 * @param range содержит диапазон значений меню.
	 * @return введенную строку
	 */
	public int ask(String question, List<Integer> range) {
		int key = Integer.valueOf(this.ask(question));
		boolean exist = false;
		for (int value : range) {
			if (value == key) {
				exist = true;
				break;
			}
		}
		if (exist) {
			return key;
		} else {
			throw new MenuOutException("out of menu range.");
		}
	}
}