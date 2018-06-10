package ru.job4j.search;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */

import java.util.ArrayList;
import java.util.List;

public class CoffeeMachine {
	public int[] changes(int value, int price) {
		int change = value - price;
		int[] money = {10, 5, 2, 1};
		int[] changeArray = new int[4];
		changeArray[0] = change / 10;
		changeArray[1] = (change % 10) / 5;
		changeArray[2] = (change - changeArray[0] * 10 - changeArray[1] * 5) / 2;
		changeArray[3] = (change - changeArray[0] * 10 - changeArray[1] * 5) % 2;

		int[] ch = new int[changeArray[0] + changeArray[1] + changeArray[2] + changeArray[3]];
		int index = 0;
			for (int j = 0; j < 4; j++) {
				for (int i = 0; i < changeArray[j]; i++) {
					ch[index] = money[j];
					index++;
				}
		}
		return ch;
	}
}