package ru.job4j.prof.dopclass;

/**
 * класс пациенты, коструктор , методы унаследованыиз класса Human
 * диагноз по умолчанию поставим что он не проходил обследование
 * здоровье будет ренерироваться рандомно при рождении  так сказать сколько бог даст))
 */
public class Pacient extends Human {
    private double health;
    private Diagnoz diagnoz;

    public Diagnoz getDiagnoz() {
        return diagnoz;
    }

    public double getHealth() {
        return health;
    }



    public void setHealth(double health) {
        this.health += health;
    }

    public Pacient(String name, Sex sex, int age) {
        super(name, sex, age);
        this.diagnoz = Diagnoz.НЕ_ПРОХОДИЛ_ОБСЛЕДОВАНИЕ;
        this.health = 0 + Math.random() * 100;
    }
    public void setDiagnoz(Diagnoz diagnoz) {
        this.diagnoz = diagnoz;
    }
}
