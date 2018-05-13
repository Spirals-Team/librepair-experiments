	/**
 * Объявление пакета, в котором находится класс.
 */
package kr.KR;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
/**
 * Добавление библиотеки для тестирования.
 */
import org.junit.jupiter.api.Test;
/**
 * Добавление библиотек пря проверки результата теста.
 */
public class CalcTest6{
	/**
	 * Создание тестовых методов.
	 */
	MainForm CalcTest6;

    @BeforeEach
   void setUp() {
    	CalcTest6 = new MainForm();
    }
    @Test
	void testn6() {
    	/**
		   * Проверка 2й формы-Для производственных помещений.
		   * проверяет не пустая ли переменная которая определяет значение напряжения в сети.
		   */
		assertNotNull(MainForm.n2);
	}
}