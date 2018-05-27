package ru.job4j;
/**
* Class Элементарный калькулятор.
* @author Michael Hodkov
*/
public class Calculate {

	/**
	 * Переменная для хранения результата вычислений.
	 */
	private double result;

	/**
	 * Method Сложения.
	 * @param first
	 * @param second
	 */
	public void add(double first, double second) {
		this.result = first + second;
	}

	/**
	 * Method Вычитания.
	 * @param first
	 * @param second
	 */
	public void subtract(double first, double second) {
		this.result = first - second;
	}

	/**
	 * Method Деления.
	 * @param first
	 * @param second
	 */
	public void div(double first, double second) {
		this.result = first / second;
	}

	/**
	 * Method Умножения.
	 * @param first
	 * @param second
	 */
	public void multiple(double first, double second) {
		this.result = first * second;
	}

	/**
	 * Method возвращает значение переменной result.
	 * @return result.
	 */
	public double getResult() {
		return this.result;
	}
}