package ru.job4j.loop;
/**
 *Класс для построения пирамиды в псевдографике.
 *@author ifedorenko
 *@since 15.08.2017
 *@version 1
 */
public class Paint {
	/**
	 * Основной метод в программе.
	 *
	 * @param h высота пирамиды
	 * @return result возвращает нашу пирамиду
	 */
	public String piramid(int h) {
		StringBuilder builder = new StringBuilder();
		String result;
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < h + i; j++) {
				if (j < h - 1 - i) {
				builder.append(" ");
				} else {
				builder.append("^");
				}
			}
			builder.append(System.getProperty("line.separator"));
		}
		result = builder.toString();
		return result;
	}
}
