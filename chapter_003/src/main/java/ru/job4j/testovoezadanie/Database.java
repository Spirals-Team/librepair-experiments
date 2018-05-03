package ru.job4j.testovoezadanie;
/**
 * Класс для хранения базы данных справочников
 *
 * @author Alexande Kaleganov
 * @since 23/04/2018
 */

import java.util.ArrayList;
import java.util.List;

public class Database {
    private ArrayList<String> spravocnik = new ArrayList<String>();

    public Database() {
    }

    /**
     * создадим метод добавления в базу, если нам не удасться добавить элемент то метод вернёт false
     * чтобы небыло дублей в базе
     *
     * @param s
     */
    public boolean addDeportament(String s) {
        boolean res = false;
        if (!this.spravocnik.contains(s)) {
            this.spravocnik.add(s);
            res = true;
        }
        return res;
    }

    public void addDeportamentList(List<String> spisok) {
        this.spravocnik.addAll(spisok);
    }

    public ArrayList<String> getSpravocnik() {
        return spravocnik;
    }

    @Override
    public String toString() {
        return "Database{" + "spravocnik=" + "\n" + spravocnik + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Database database = (Database) o;

        return spravocnik.equals(database.spravocnik);
    }

    @Override
    public int hashCode() {
        return spravocnik.hashCode();
    }
}
