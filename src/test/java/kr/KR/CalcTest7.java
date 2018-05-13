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
public class CalcTest7{
	/**
	 * Создание тестовых методов.
	 */
	ComponentForm CalcTest7;

    @BeforeEach
   void setUp() {
    	CalcTest7 = new ComponentForm();
    }
    @Test
   	void testn7() {
   			/**
   			   * Проверка формы для компонентов на возможность отображения.
   			   */
   	    assertTrue(ComponentForm.frame.isVisible());
   	}
}