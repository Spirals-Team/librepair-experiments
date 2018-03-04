package ru.job4j.profession;

import ru.job4j.profession.model.Position;

/**
 * Class that describes all base characteristic of professions.
 *
 * @author Pyotr Kukharenka
 * @since 27.11.2017
 */

public abstract class Profession {
    private String name;
    private String surname;
    private String sex;
    private double experience;
    private String specialization;
    private Position position;

    /**
     * Base constructor.
     *
     * @param name    - person name.
     * @param surname - person surname.
     */
    public Profession(String name, String surname) {
        this.name = name;
        this.surname = surname;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getSex() {
        return sex;
    }

    public double getExperience() {
        return experience;
    }

    public String getSpecialization() {
        return specialization;
    }

    public Position getPosition() {
        return position;
    }
}
