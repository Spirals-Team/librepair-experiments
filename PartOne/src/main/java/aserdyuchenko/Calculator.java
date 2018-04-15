package aserdyuchenko;
/**
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 12.09.2016
 *
 */
public class Calculator {
/**
 * @param result - result.
 */
	private double result;
/**
 * getX().
 * @return this.x - this.x.
 */
	public double getResult() {
		return this.result;
	}
/**
 * Метод add выполняет сложение чисел first и second. Затем записывает значение в поле result.
 * @param first - first.
 * @param second - second.
 */
	public void add(double first, double second) {
		this.result = first + second;
	}
/**
 * Метод substruct выполняет разность чисел first и second. Затем записывает значение в поле result.
 * @param first - first.
 * @param second - second.
 */
	public void substruct(double first, double second) {
		this.result = first - second;
	}
/**
 * Метод div выполняет деление чисел first и second. Затем записывает значение в поле result.
 * @param first - first.
 * @param second - second.
 */
	public void div(double first, double second) {
		this.result = first / second;
	}
/**
 * Метод multiple выполняет умножение чисел first и second. Затем записывает значение в поле result.
 * @param first - first.
 * @param second - second.
 */
	public void multiple(double first, double second) {
		this.result = first * second;
	}
}
