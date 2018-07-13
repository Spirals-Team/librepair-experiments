package ru.job4j.department;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Класс содержит методы для работы с списком наименований отделов
 */
public class SortDepart {

    /**
     * Метод конвертирует наименование отдела из текста в объект Depart
     * @param name текстовое название
     * @return объект отдел
     */
    private Depart convertOne(String name) {
        Depart result = new Depart();
        for (String subst : name.split("/")) {
            result.getNames().add(subst);
        }
        return result;
    }

    /**
     * Метод конвертирует список отделов из массива текста в список объектов
     * @param names массив текста с наименованиями отделов
     * @return список отдела в виде списка объектов
     */
    public ArrayList<Depart> convert ( String [] names) {
        ArrayList<Depart> result = new ArrayList<>();
        for (String name: names) {
            result.add(convertOne(name));
        }
        return result;
    }

    /**
     * Метод добавляет в список отделов недостающие отделы, наименования которых есть существующем спсике
     * @param names первичный список отделов
     * @return дополненый списко отделов
     */
    public ArrayList<Depart> normalization ( ArrayList<Depart> names) {
        ArrayList<Depart> result = new ArrayList<>(names);
        Depart mid =  new Depart();
        for (Depart name2: names) {
            mid = new Depart();
            for (String str: name2.getNames()) {
                mid.getNames().add(str);
                if (! result.contains(mid)) {
                    result.add(new Depart(mid));
                }
            }
        }
        return result;
    }

    /**
     * Метод сортирует список отделов по возрастанию (natural order)
     * @param names исходный список
     * @return результат сортировки
     */
    public ArrayList<Depart> sortNatural(ArrayList<Depart> names) {
        Comparator<Depart> comp = new Comparator<Depart>() {
            @Override
            public int compare(Depart o1, Depart o2) {
                return o1.compareTo(o2);
            }
        };
        ArrayList<Depart> result = new ArrayList<>(names);
        result.sort(comp);
        return result;
    }

    /**
     * Метод сортирует список отделов по убыванию
     * @param names исходный список
     * @return результат сортировки
     */
    public ArrayList<Depart> sortDec(ArrayList<Depart> names) {
        Comparator<Depart> comp = new Comparator<Depart>() {
            @Override
            public int compare(Depart o1, Depart o2) {
                int result = 0;
                int sz = o1.size() < o2.size() ? o1.size() : o2.size();
                for (int i = 0; i < sz; i++) {
                    if (o1.get(i).equals(o2.get(i))) {
                        continue;
                    }
                    result = o1.get(i).compareTo(o2.get(i)) == -1 ? 1 : -1;
                    break;
                }
                if (result == 0 && o1.size() != o2.size()) {
                    result = o1.size() < o2.size() ? -1 : 1;
                }
                return result;
            }
        };
        ArrayList<Depart> result = new ArrayList<>(names);
        result.sort(comp);
        return result;
    }
}

