package ru.job4j.prof.dopclass;

/**
 * класс студенты, коструктор , методы унаследованыиз класса Human
 */

public class Student extends Human {
    private int intelect;
    private Knijka knijka;
    public Student(String name, Sex sex, int age) {
        super(name, sex, age);
        this.intelect = 70;
        this.knijka = Knijka.ПОПЫТОК_СДАЧИЭКЗАМЕНА_НЕБЫЛО;
    }

    public int getIntelect() {
        return intelect;
    }

    public Knijka getKnijka() {
        return knijka;
    }

    public void setKnijka(boolean popitka) {
        if (popitka) {
            this.knijka = Knijka.ЗАЧЁТ;
        } else {
            this.knijka = Knijka.НЕЗАЧЁТ;
        }
    }

    public void setIntelect(int intelect) {

        this.intelect += intelect;
    }
}
