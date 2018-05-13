	/**
 * Объявление пакета, в котором находится класс.
 */
package kr.KR;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
/**
 * Добавление библиотеки для тестирования.
 */
import org.junit.jupiter.api.Test;
/**
 * Добавление библиотек пря проверки результата теста.
 */
public class CalcTest3{
	/**
	 * Создание тестовых методов.
	 */
	ComponentForm CalcTest3;

    @BeforeEach
   void setUp() {
    	CalcTest3 = new ComponentForm();
    }
    @Test
	void testn2() {
			/**
			   * итоговая проверка, того установлен ли шрифт для кнопки
			   */
		  assertTrue(ComponentForm.btnNewButton.isFontSet());
	}
}