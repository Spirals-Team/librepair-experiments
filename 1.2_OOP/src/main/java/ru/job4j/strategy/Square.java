package ru.job4j.strategy;
/**
 *Класс Square.
 *@author ifedorenko
 *@since 26.08.2017
 *@version 1
 */
public class Square implements Shape {
    /**
     * @param height сторона квадрата
     */
    private int height;

    /**
     * Конструктор квадрата.
     * @param height указываем, чему равна сторона квадрата.
     */
    public Square(int height) {
        this.height = height;
    }

    /**
     * Основной метод. Создает квадрат.
     * @return квадрат
     */
    public String pic() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.height; j++) {
                if (i == 0 || i == this.height - 1 || j == 0 || j == this.height - 1) {
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