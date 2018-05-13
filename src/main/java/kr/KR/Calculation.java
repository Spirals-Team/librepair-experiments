/**
 * объявление пакета
 */
package kr.KR;
/**
 * Класс для расчётов
 */
public class Calculation {
    public static float sums(float k1, float r1) {
    	/**
    	 * Переменная для выполнения расчётов
    	 */
    	float sum;
    	/**
    	 * Выполнение рассчетов
    	 */
    	sum = (float) ((k1*1.2)/r1);
    	/**
    	 * Возвращение значения переменной
    	 */
    	return sum;
    }
}