package ru.job4j.shape;

/**
 * Class Square.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 09.10.2017
 */
public class Square implements Shape {
    @Override
    public String pic() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("+++++\n");
        stringBuilder.append("+   +\n");
        stringBuilder.append("+++++");
        return stringBuilder.toString();
    }
}
