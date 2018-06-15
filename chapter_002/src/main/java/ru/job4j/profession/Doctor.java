package ru.job4j.profession;

public class Doctor extends Profession {

    public Diagnose heal(Patient patient) {

        return new Diagnose();
    }
}
