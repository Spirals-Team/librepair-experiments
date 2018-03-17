package ru.job4j.profession;
/**
 * Doctor.
 *
 * @author Hincu Andrei (andreih1981@gmail.com)
 * @version $Id$
 * @since 0.1
 */

public class Doctor extends Profession {
    /**
     * Специальность врача.
     */
   private String diplom;

    /**
     * Конструктор класса.
     * @param name - имя.
     * @param age - возраст.
     * @param diplom - специальность.
     */
    public Doctor(String name, int age, String diplom) {
        super(name, age);
        this.diplom = diplom;
    }

    /**
     * Лечение.
     * @param patient - пациент.
     * @return - лечение.
     */
    public String heal(Profession patient) {
        return this.getName() + " лечит " + patient.getName();
    }
}
