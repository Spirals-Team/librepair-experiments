/**
 * Устанавливаем принадлежность класса к пакету
 */
package com.sanifrey.test1;

import javax.swing.JOptionPane;

/**
 * Объявляем класс с модификатором public
 */
public class Formula {
	/**
	 * Экземпляр класса Planir
	 */
	
	/**
	 * Объявляем приватные статические переменные. FreeArea - значение свободной
	 * площади в комнате. AllArea - значение всей площади комнаты.
	 */
	private static float FreeArea;
	private static float AllArea;
	private static int Amount = 0;
	private static Objects[] Obj = new Objects[1024];
	private int number=1;

	/**
	 * Вызываем конструктор
	 */

	/**
	 * Приватный метод для расчёта свободной площади в комнате
	 * 
	 */
	private String AddObject(float width, float length) {
		if (width != 0.0 && length != 0.0) {
			Amount += 1;
			Obj[Amount] = new Objects();
			Obj[Amount].setArea(width * length);
			Obj[Amount].setWidth(width);
			Obj[Amount].setLength(length);
		} else {
			JOptionPane.showMessageDialog(null, "Заполните поля ненулевыми значениями!");
		}
		return String.valueOf(Amount);
	}

	private String DeleteObjects() {
		Amount = 0;
		FreeArea = AllArea;
		return String.valueOf(Amount);
	}

	private String FormulaFree(float area) {
		/**
		 * Выполняем расчёт свободной площади в комнате
		 */
		AllArea = area;
		FreeArea = AllArea;
		for (number = 1; number < Amount+1; number++) {
			FreeArea = FreeArea - Obj[number].getArea();
		}
		/**
		 * Если значение свободной площади(FreeArea) в комнате меньше нуля(что является
		 * логической ошибкой), то присваиваем значение свободной площади(FreeArea) к
		 * нулю.
		 */
		if (FreeArea < 0)
			FreeArea = 0;
		/**
		 * Вызываем метод FillTextField_3 для заполнения в форме планировщик поля
		 * textField_3 значением FreeArea
		 */
		return String.valueOf(FreeArea);
	}

	/**
	 * Публичный метод для вызова приватного метода FormulaFree.
	 * 
	 * @return
	 */
	public String PFormula(String area) {
		try {
			FormulaFree(Float.parseFloat(area));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ошибка в расчёте свободной площади! \nПоля заполнены неверно!");
		}
		return String.valueOf(FreeArea);

	}

	/**
	 * Геттер для получения значения свободной площади в комнате.
	 */
	public static float getFreeArea() {
		return FreeArea;
	}

	/**
	 * Геттер для получения значения всей площади в комнате.
	 */
	public static float getAllArea() {
		return AllArea;
	}

	public String PublicAddObject(String width, String length) {
		try {
			AddObject(Float.parseFloat(width), Float.parseFloat(length));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ошибка во время добавления объекта! \nПоля заполнены неверно!");
		}
		return String.valueOf(Amount);

	}

	public String PublicDeleteObjects() {
		return DeleteObjects();
	}

	public static Objects[] getObj() {
		return Obj;

	}

	public static int getAmount() {
		return Amount;
	}

}
