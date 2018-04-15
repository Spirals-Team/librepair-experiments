package ru.aserdyuchenko;
/*
Исключения делятся на три типа: контролируемые исключения (checked) и
неконтролируемые исключения (unchecked), к которым относятся ошибки (Errors) и
исключения времени выполнения (RuntimeExceptions, потомок класса Exception).
*/

/**
 * Exemple exeption.
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 28.11.2016
 */
public class SecondQuestion {
/**
 * Method for example Exception.
 * @param first - first number.
 * @param second - second number.
 */
	public void exceptionExample(int first, int second) {
		try {
/**
 * @param result - the result of the division.
 */
			int result = first / second;
		} catch (ArithmeticException example) {
              System.out.print("The arithmetic operation error.");
        }
	}
}
