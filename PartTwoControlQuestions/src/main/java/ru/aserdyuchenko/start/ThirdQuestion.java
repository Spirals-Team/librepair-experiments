package ru.aserdyuchenko;
/*
Как недопустить изменение массива. Только его чтение.
С помощью ключевого слова private, не допускаем изменение.
С помощью метода gerARRAY, разрешаем только его чтение.
*/
/**
 * Exemple how work exeption, if you want change array.
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 28.11.2016
 */
public class ThirdQuestion {
/**
 * @param array - array for example exception.
 */
private static final int[] 	ARRAY = {1, 2};
/**
 * Method for get this array.
 * @return this.ARRAY - this array.
 */
	public int[] getARRAY() {
		return this.ARRAY;
	}
}
