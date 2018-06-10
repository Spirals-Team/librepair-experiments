package ru.job4j;

import java.io.BufferedReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
 
 public class Contains {
	/**
	* Метод проверяет содержил строка подстроку.
	* @param  строка и подстрока.
	* @return истина или ложь.
	*/  
	public boolean contains(String origin, String sub) {
		char[] originArray = origin.toCharArray();
		char[] subArray = sub.toCharArray();
		char[] res = new char[subArray.length];
		int index = 0;
		for (int i = 0; i < subArray.length; i++) {
			for (int j = index; j < originArray.length; j++) {
				index++;
				if (subArray[i] == originArray[j]) {
					res[i] = originArray[j];
					break;
				} 
			}
		}
	return Arrays.equals(res, subArray);	
	}
}