package ru.job4j.strategy;
/**
 *Класс Triangle.
 *@author ifedorenko
 *@since 26.08.2017
 *@version 1
 */
public class Triangle implements Shape {
	/**
	 * @param высота треугольника.
	 */
	private int height;
	/**
	 * Конструктор треугольника.
	 * @param height высота треугольника
	 */
	public Triangle(int height) {
		this.height = height;
	}
	/**
	 * Основной метод. Создает треугольник.
	 * @return треугольник
	 */
	public String pic() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.height + i; j++) {
				if (i == this.height - 1 || j == this.height - 1 - i || j == this.height - 1 + i) {
					builder.append("x");
				} else {
					builder.append(" ");
				}
			}
			builder.append(System.getProperty("line.separator"));
		}
		return builder.toString();
	}
}