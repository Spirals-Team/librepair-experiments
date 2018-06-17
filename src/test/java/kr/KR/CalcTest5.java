	/**
 * Объявление пакета, в котором находится класс.
 */
package kr.KR;
/**
 * Добавление библиотек для проверки результата теста.
 */
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
/**
 * Добавление библиотеки для тестирования.
 */
import org.junit.jupiter.api.Test;

public class CalcTest5{
	/**
	 * Создание тестовых методов.
	 */
    @BeforeEach
   void setUp() {
    	new MainForm();
    }
    @Test
	void testn5() {
			/**
			   * Проверка 1й формы-Для жилых помещений. 
			   * Проверяет верное ли значение задается для TxtBox2(Напряжение в сети).
			   */
	    assertTrue(MainForm.n1==220);
	}
}
