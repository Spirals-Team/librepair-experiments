package ru.job4j.arr;

/**
 * @author Alexander KAleganov
 * решал дополнительно задачки с форума программистов
 * программа получает строковое значение
 * к примеру 2+1 и переводит строку в строковый массив
 * далее у нас есть заранее готовый массив, в котором есть арифметические знаки
 * далее  программа ищет знаки в полученном массиве и если находит то
 * производит действия с элементам  i-1  и i+1
 */
public class Calk {
    public static double kkkalCullator(String primer) {
        double result = 0;
        String[] operator = new String[]{"+", "-", "*", "/"};
        String[] strprimer = new String[primer.length()];
        for (int i = 0; i < primer.length(); i++) {
            strprimer[i] = primer.substring(i, i + 1);
        }
        for (int j = 0; j < primer.length(); j++) {
            if (operator[0].equals(strprimer[j])) {
                double x = Double.valueOf(strprimer[j - 1]);
                double y = Double.valueOf(strprimer[j + 1]);
                result = x + y;
            } else if (operator[1].equals(strprimer[j])) {
                double x = Double.valueOf(primer.substring(j - 1));
                double y = Double.valueOf(primer.substring(j + 1));
                result = x - y;
            } else if (operator[2].equals(strprimer[j])) {
                double x = Double.valueOf(primer.substring(j - 1));
                double y = Double.valueOf(primer.substring(j + 1));
                result = x * y;
            } else if (operator[3].equals(strprimer[j])) {
                double x = Double.valueOf(primer.substring(j - 1));
                double y = Double.valueOf(primer.substring(j + 1));
                result = x / y;
            }
        } return result;
    }
}
