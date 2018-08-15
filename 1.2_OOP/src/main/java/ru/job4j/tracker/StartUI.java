package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;

/**
 *Класс StartUI.
 *@author ifedorenko
 *@since 26.08.2017
 *@version 1
 */
public class StartUI {
	/**
	 *@param хранит ссылку на объект .
	 */
	private Input input;
	/**
	 *@param хранит ссылку на объект .
	 */
	private Tracker tracker;
	/**
	 * Базовый конструктор для работы с StartUI.
	 * @param input объект типа Input
	 * @param tracker объект типа Tracker
	 */
	public StartUI(Input input, Tracker tracker) {
		this.input = input;
		this.tracker = tracker;
	}
	/**
	 * Основной метод для работиы с меню.
	 */
	public void allAction() {
		MenuTracker menu = new MenuTracker(this.input, this.tracker);
		List<Integer> range = new ArrayList<>();
		menu.fillActions();
		for (int i = 0; i < menu.getActionsLentgh(); i++) {
			range.add(i);
		}
		do {
			menu.show();
			menu.select(input.ask("select:", range));
		} while (!"y".equals(this.input.ask("Exit?(y): ")));
	}
	/**
	 * Запуск программы.
	 * @param args для ввод из консоли
	 */
	public static void main(String[] args) {
		Input input = new ValidateInput();
		Tracker tracker = new Tracker();
		new StartUI(input, tracker).allAction();
	}
}