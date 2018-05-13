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
public class CalcTest8{
	/**
	 * Создание тестовых методов.
	 */
	ComponentForm CalcTest8;

    @BeforeEach
   void setUp() {
    	CalcTest8 = new ComponentForm();
    }
    @Test
	void testn8() {
			/**
			   * Проверка того, не является ли поле TxtBox2(напряжение в ести) пустым.
			   */
		assertNotNull(ComponentForm.TxtBox2.getText());
	}
}