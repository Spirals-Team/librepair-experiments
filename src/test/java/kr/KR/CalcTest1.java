	/**
 * Объявление пакета, в котором находится класс.
 */
package kr.KR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
/**
 * Добавление библиотеки для тестирования.
 */
import org.junit.jupiter.api.Test;
/**
 * Добавление библиотек пря проверки результата теста.
 */
public class CalcTest1{
	/**
	 * Создание тестовых методов.
	 */
	Calculation CalcTest1;

    @BeforeEach
   void setUp() {
    	CalcTest1 = new Calculation();
    }
	/**
	 * Создание тестовых методов.
	 */
	@Test
	void testn1() {
		/**
		 * Максимальная мощность 300.
		 * Напряжение сети 220.
		 * Необхожимый коэфицент для расчёта 1.2.
		 */
		  float k1=(float)330,r1=220;
			/**
			   * Проверяем верное ли значение получается в итоге рассчетов.
			   */
	    assertEquals((float)1.8, Calculation.sums(k1, r1));
	}
}