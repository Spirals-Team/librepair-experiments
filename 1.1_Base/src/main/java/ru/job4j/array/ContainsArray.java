package ru.job4j.array;
/**
*Класс выполняющий проверку двух строк.
*@author ifedorenko
*@since 16.08.2017
*@version 1
*/
public class ContainsArray {
	/**
	*Основной метод в программе.
	*@param origin Строка в которой ищем
	*@param sub строка что ищем
	*@return возвращает результат проверки
	*/
	public boolean contains(String origin, String sub) {
		char[] source = origin.toCharArray();
		char[] check = sub.toCharArray();
		int i = 0; //счетчик для массива check
		for (int j = 0; j < source.length; ++j) {
			if (check[i] == source[j]) { //проверяем на совпадение символы
				i++; //если нашли совпадение, то проверяем следующие элементы в массивах
				if (i == check.length - 1) { //проверка на полное совпадение
					return true;
				}
			} else if (i == 0) {
				continue;
			} else {  //если нет совпадений берем снова первый элемент из массива check и проверяем с элементом, на котором остановились в массиве source
				i = 0;
				j--;
			}
		}
		return false;
	}
}