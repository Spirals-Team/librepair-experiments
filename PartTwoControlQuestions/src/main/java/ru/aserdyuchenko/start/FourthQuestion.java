package ru.aserdyuchenko;
/*
Есть массив, из открывающихся и закрывающихся скобок.
Нужно проверить, все ли скобки закрыты.
*/
/**
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 28.11.2016
 */
public class FourthQuestion {
/**
 * @param check - this array is true or false.
 */
private boolean check = false;
/**
 * Method for check array.
 * @param array - array for check.
 * @return check - result of check.
 */
	public boolean checkArray(String[] array) {
/**
 * @param countO - count Open Bracket.
 */
		int countO = 0;
/**
 * @param countC - count Close Bracket.
 */
		int countC = 0;
		for (String index : array) {
			if (index.equals("(")) {
				countO++;
			} else {
				countC++;
			}
			if (countO < countC) {
				check = false;
				break;
			} else if (countO != countC) {
				check = false;
			} else {
				check = true;
			}
		}
	return check;
	}
}
