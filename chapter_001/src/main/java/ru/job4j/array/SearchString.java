package ru.job4j.array;

/**
 * Class SearchString. Проверка, что одно слово находится в другом слове.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 20.09.2017
 */
public class SearchString {
    /**
     * Method contains. Принимает на вход массив на выходе вернет массив перевернутый по часвой стрелке.
     *
     * @param origin исходный текст
     * @param sub то что будем искать
     * @return вернет true если в origin будет найден текст sub
     */
    public boolean contains(String origin, String sub) {
        char[] originArray = origin.toCharArray();
        char[] subArray = sub.toCharArray();

        for (int i = 0; i < originArray.length; i++) {
            if (originArray[i] == subArray[0]) {
                for (int j = 0; j < subArray.length; j++) {
                    if (originArray[i + j] != subArray[j]) {
                        break;
                    }
                    if (j == subArray.length - 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
