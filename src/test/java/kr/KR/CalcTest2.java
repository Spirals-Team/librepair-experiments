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
public class CalcTest2{
	/**
	 * Создание тестовых методов.
	 */
	ComponentForm CalcTest2;

    @BeforeEach
   void setUp() {
    	CalcTest2 = new ComponentForm();
    }
    @Test
	void testn2() {
	    /**
		 * Значение строки которой должен соотвествовать label_1.
		 */
		  String k1="Максимальная мощность, Вт";
			/**
			   * итоговая проверка, выясняем правильно ли написана строка в label_1.
			   */
		  assertEquals(k1, ComponentForm.label_1.getText());
	}
}