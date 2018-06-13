	/**
 * Объявление пакета, в котором находится класс.
 */
package kr.KR;
/**
 * Добавление библиотек для проверки результата теста.
 */
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
/**
 * Добавление библиотеки для тестирования.
 */
import org.junit.jupiter.api.Test;

public class CalcTest6{
	/**
	 * Создание тестовых методов.
	 */
    @BeforeEach
   void setUp() {
    	new MainForm();
    }
    @Test
	void testn6() {
    	/**
		   * Проверка 2й формы-Для производственных помещений.
		   * Проверяет не пустая ли переменная которая определяет значение напряжения в сети.
		   */
		assertNotNull(MainForm.n2);
	}
}
