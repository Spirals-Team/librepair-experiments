package ru.job4j.komparestring;
/**
 * компоратор для стринга
 */

import java.util.Comparator;

public class ListCompare implements Comparator<String> {
    @Override
    public int compare(String left, String right) {
        int result = 0;
        for (int i = 0; i < left.length() && i < right.length(); i++) {
            result = Integer.compare(left.charAt(i), right.charAt(i)); //тут мы получаем значение символа и проверяем какое больше или меньше
            if (result != 0) {  //и если мы получим -1 или 1 то цикл завершиться,  в противном случае мы продолжми меребирать элементы
                break;
            }
        }
        if (result == 0 && left.length() != right.length()) { //если результ всёже будет всёравно равен 0 то мы проверим длинну строк, и если длинна окажется разная то
            result = Integer.compare(left.length(), right.length()); //мы   выясним какие слово короче,  результ примет либо 1 либо минус 1
        }                                              //ели строки окажутся равной длины , то результ останется нулевым, и мы просто вернём ноль и строки меняться местами не будут
        return result;
    }
}