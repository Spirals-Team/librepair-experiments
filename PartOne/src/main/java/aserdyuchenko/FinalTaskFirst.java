package aserdyuchenko;

/**
 * Тестовое задание. Часть 001.
 * Создать программу проверяющую, что строка sub является подстрокой origin.
 * boolean contains(String origin, String sub).
 * Нельзя использовать String.indexOf String.contains.
 * Нужно преобразовать строку в массив символов и проверить.
 *
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 20.09.2016
 */
public class FinalTaskFirst {
/**
 * Преобразование оригинальной строки в массив.
 * @param origin - origin.
 * @return originArray - originArray.
 */
    String[] convertOrigin(String origin) {
        String[] originArray = origin.split(" ");
        return originArray;
    }
/**
 * Преобразование второстепенной строки в массив.
 * @param sub - sub.
 * @return subArray - subArray.
 */
    String[] convertSub(String sub) {
        String[] subArray = sub.split(" ");
        return subArray;
    }
/**
 * Сравнение двух массивов.
 * Проверка, является ли массив sub частью массива origin.
 * @param originArray - originArray.
 * @param subArray - subArray.
 * @return result - result.
 */
    Boolean comparison(String[] originArray, String[] subArray) {
        boolean result = false;
        for (int indexFirst = 0; indexFirst < originArray.length; indexFirst++) {
            for (int indexSecond = 0; indexSecond < subArray.length; indexSecond++) {
                if (originArray[indexFirst].equals(subArray[indexSecond])) {
                    result = true;
                }
            }
        }
        return result;
    }
}
