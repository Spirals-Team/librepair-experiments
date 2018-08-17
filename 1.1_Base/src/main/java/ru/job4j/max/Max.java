package ru.job4j.max;
/**
 *Класс помогает узнать, какое из двух чисел больше.
 *@author ifedorenko
 *@since 14.08.2017
 *@version 1
 */
public class Max {
	/**
	*Возвращает большее число из двух.
	*@param first содержит первое число
	*@param second содержит второе число
	*@return Большее из двух
	*/
	public int max(int first, int second) {
		return first > second ? first : second;
	}
		/**
	*Возвращает большее число из трех.
	*@param first содержит первое число
	*@param second содержит второе число
	*@param third содержит третье число
	*@return Большее из трех
	*/
	public int max(int first, int second, int third) {
		return max(first, max(second, third));
	}
}