package ru.job4j.strategy;

public class Square implements Shape {
    public static String newline = System.getProperty("line.separator");

    public String draw() {
        StringBuilder pic = new StringBuilder();
        pic.append("+ + + +" + newline);
        pic.append("+     +" + newline);
        pic.append("+     +" + newline);
        pic.append("+ + + +" + newline);
        return pic.toString();
    }

    public static void main(String[] args) {
        Square s = new Square();
        System.out.println(s.draw());
    }
}