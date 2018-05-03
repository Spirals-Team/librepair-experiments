package ru.job4j.testovoezadanie;
/**
 * класс утилыты содержит два метода сортировки, метод рефакторинга справочника
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.ListIterator;

public class Utilites {
    private ArrayList<String> temp = new ArrayList<>();

    /**
     * прямая сортировка
     *
     * @param database
     */
    public void sortedDataStright(Database database) {
        database.getSpravocnik().sort(String::compareTo);
    }

    /**
     * обратная сортировка
     * можено было сделать через reversOrder
     *
     * @param database
     */
    public void sortedDataRewers(Database database) {
        database.getSpravocnik().sort(new Comparator<String>() {
            @Override
            public int compare(String left, String right) {
                int result = 0;
                result = left.compareTo(right);
                if (result == 1) {
                    result = -1;
                } else if (result == -1) {
                    result = 1;
                }
                return result;
            }
        });
    }

    /**
     * в этом методе мы будем проверять нашу базу депортаментов
     * и в случае отсутствия строк верхнего уровня будем добавлять их в базу
     * сделал так: сосдав временную переменную, в которую буду передавать строки нижнего уровня,
     * чтобы потом брать и проверять  нашь список департаментов через листитератор мы сможем добавлять элементы
     * PS: жутко понравились лямбда метода фор
     *
     * @param database
     */
    public void refactorDatabase(Database database) {
        ListIterator<String> data = database.getSpravocnik().listIterator();
        while (data.hasNext()) {
            String temp = data.next();
            if (temp.length() == 11) {
                for (int i = 6; i >= 2; i -= 4) {
                    temp = temp.substring(0, i);
                    if (!database.getSpravocnik().contains(temp)) {
                        data.add(temp);
                    }
                }
            }
        }
    }
}
