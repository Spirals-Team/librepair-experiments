package ru.job4j.department;

import java.util.ArrayList;

/**
 *  Класс Depart предназначен для описание места отдела в структуре компании через последовательный
 *  список вышестоящих отделов
 */
public class Depart implements Comparable {

    private ArrayList<String> names = new ArrayList<>();

    public ArrayList<String> getNames() {
        return names;
    }

    // Конструктор по умолчанию
    Depart() {
    }

    // Конструктор с клонированием объекта
    Depart (Depart dep) {
        for (String str: dep.names) {
            names.add(str);
        }
    }

    // Конструктор из массива строк
    Depart ( String[] strs) {
        for ( String str : strs) {
            names.add(str);
        }
    }

    public int size() {
        return names.size();
    }

    public String get( int i) {
        return names.get(i);
    }

    // Определяем natural order
    @Override
    public int compareTo(Object o) {
        Depart depart = (Depart) o;
        int result = 0;
        int sz = this.names.size() < depart.names.size() ? this.names.size() : depart.names.size();
        for (int i = 0; i < sz; i++) {
            if (this.names.get(i).equals(depart.names.get(i))) {
                continue;
            }
            result = this.names.get(i).compareTo(depart.names.get(i));
            break;
        }
        if (result == 0 && this.names.size() != depart.names.size()) {
            result = this.names.size() < depart.names.size() ? -1 : 1;
        }
        return result;
    }

    // Определяем красивый вывод
    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("Depart ");
        for (String str: names) {
            result.append("/");
            result.append(str);
        }
        return result.toString();
    }

    // Определяем равенство объектов
    @Override
    public boolean equals(Object o) {
        Depart depart = (Depart) o;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() || this.names.size()!= depart.names.size()) return false;
        for ( int i = 0; i < this.names.size(); i++) {
            if ( ! this.names.get(i).equals(depart.names.get(i))) {
                return false;
            }
        }
        return true;
    }

    // За компанию с equals
    @Override
    public int hashCode() {
        return names != null ? names.hashCode() : 0;
    }
}
