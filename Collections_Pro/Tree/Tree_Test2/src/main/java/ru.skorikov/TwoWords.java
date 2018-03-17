package ru.skorikov;


import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 19.12.17
 * @ version: java_kurs_standart
 *
 * Состоят ли слова из одинаковых букв.
 */
public class TwoWords {
    /**
     * Рабочий лист.
     */
    private List<String> list = new LinkedList<>();


    /**
     * Проверяем два слова одинаковой длины.
     * Логика такая - записываем в List слово побуквенно.
     * удаляем из листа буквы второго слова.
     * если слова состоят из одинавого набора букв - размер листа должен стать равен 0.
     *
     * @param str1 слово 1.
     * @param str2 слово 2.
     * @return true - одной длины и состоят из одинаковых букв.
     */
    public boolean isWordsConsistOfIdenticalLetters(String str1, String str2) {
        boolean isWCOIL = false;

        if (str1.length() == str2.length()) {
            for (char ch : str1.toCharArray()) {
                list.add(String.valueOf(ch));
            }
            for (char ch : str2.toCharArray()) {
                list.remove(String.valueOf(ch));
            }
            if (list.size() == 0) {
                isWCOIL = true;
            }
        }
        return isWCOIL;
    }
}
