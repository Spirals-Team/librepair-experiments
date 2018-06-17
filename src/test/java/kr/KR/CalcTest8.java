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
public class CalcTest8{
	/**
	 * Создание тестовых методов.
	 */
    @BeforeEach
   void setUp() {
    	new ComponentForm();
    }
    @Test
	void testn8() {
			/**
			   * Проверка того, не является ли поле TxtBox2(напряжение в ести) пустым.
			   */
		assertNotNull(ComponentForm.TxtBox2.getText()=="kek");
	}
}
