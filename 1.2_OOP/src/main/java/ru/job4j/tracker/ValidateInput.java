package ru.job4j.tracker;

import java.util.List;

/**
 *Класс  ValidateInput.
 *@author ifedorenko
 *@since 26.08.2017
 *@version 1
 */
public class  ValidateInput extends ConsoleInput {
	/**
	 * Основной метод в классе.
	 * @param question Вопрос-подсказка для пользователя.
	 * @param range содержит диапазон значений меню.
	 * @return значение ключа
	 */
	public int ask(String question, List<Integer> range) {
		boolean invalid = true;
		int value = -1;
		do {
			try {
				value = super.ask(question, range);
				invalid = false;
			} catch (MenuOutException moe) {
				moe.printStackTrace();
				System.out.println("Please select key from menu");
			} catch (NumberFormatException nfe) {
				System.out.println("Please enter validate data again");
			}
		} while (invalid);
		return value;
	}
}