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
public class CalcTest5{
	/**
	 * Создание тестовых методов.
	 */
	MainForm CalcTest5;

    @BeforeEach
   void setUp() {
    	CalcTest5 = new MainForm();
    }
    @Test
	void testn5() {
			/**
			   * Проверка 1й формы-Для жилых помещений. 
			   * проверяет верное ли значение задается для TxtBox2(Напряжение в сети).
			   */
	    assertTrue(MainForm.n1==220);
	}
}