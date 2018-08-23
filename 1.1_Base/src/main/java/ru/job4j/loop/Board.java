package ru.job4j.loop;
/**
*Класс для построения шахматной доски.
*@author ifedorenko
*@since 14.08.2017
*@version 1
*/
public class Board {
	/**
	 * Основной метод в программе.
	 *
	 * @param width  количество столбцов в таблице
	 * @param height количество строк в таблице
	 * @return result возвращает необходимую таблицу
	 */
	public String paint(int width, int height) {
		StringBuilder builder = new StringBuilder();
		String result;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if ((i + j) % 2 == 0) {
					builder.append("x");
				} else {
					builder.append(" ");
				}
			}
			builder.append(System.getProperty("line.separator"));
		}
		result = builder.toString();
		return result;
	}
}

