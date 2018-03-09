package ru.aserdyuchenko.tracker.start;

import java.sql.SQLException; /**
 * Программа tracker. Служит для учета заявок.
 * Приложение осуществляет следующие действия:
 * 1. Добавление новой заявки
 * 2. Редактирования заявки
 * 3. Удаления заявки
 * 4. Отображение списка всех заявок
 * 5. Отображения заявок с условием фильтра
 * 6. Добавлять к заявки комментарий
 *
 * В заявке может быть следующая информация: имя, описание, дата
 * создания и список комментариев.
 */

/**
 * [#790]
 * Нужно сделать абстрактный класс BaseAction с конструктором. Который будет
 * принимать name . Сделать имплементацию данного класса от интерфейса UserAction и
 * реализовать метод info(). Отстальные методы отсавить abstract. Наследовать
 * остальные событие от класса BaseAction.
 */

/**
 * @author Anton Serdyuchenko
 * @since 27.09.2016
 * @version 5.0
 */

public class StartUI {
	private int[] ranges = new int[] {0,1,2,3,4};
	private Input input;

	public StartUI(Input input){
		this.input = input;
	}

	public void init() throws SQLException {
		Tracker tracker = new Tracker();
		MenuTracker menu = new MenuTracker(this.input, tracker);
		menu.fillActions();
		do {
			menu.show();
			menu.select(input.askMenuChoice("select: ", ranges));
		} while (!"4".equals(this.input.askMenuChoice("Exit? (4): ")));
	}

	public static void main(String[] args) throws SQLException {
		Input input = new ValidateInput();
		new StartUI(input).init();
	}
}
