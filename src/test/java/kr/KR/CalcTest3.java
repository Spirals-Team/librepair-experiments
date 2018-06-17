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

public class CalcTest3{
	/**
	 * Создание тестовых методов.
	 */
    @BeforeEach
   void setUp() {
    	new ComponentForm();
    }
    @Test
	void testn2() {
			/**
			   * Проверка того установлен ли шрифт для кнопки
			   */
		  assertTrue(ComponentForm.btnNewButton.isFontSet()==false);
	}
}
