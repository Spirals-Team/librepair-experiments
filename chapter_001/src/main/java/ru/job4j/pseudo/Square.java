package ru.job4j.pseudo;

public class Square implements Shape {
    @Override
    // Обязательное определение метода Рисовать для квадрата
    public String draw() {
        StringBuilder pic = new StringBuilder();
        pic.append("++++");
        pic.append("+  +");
        pic.append("+  +");
        pic.append("++++");
        return pic.toString();
    }
}