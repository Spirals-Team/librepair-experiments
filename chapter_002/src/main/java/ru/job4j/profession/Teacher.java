package ru.job4j.profession;
/**
 * Teacher.
 *
 * @author Hincu Andrei (andreih1981@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Teacher extends Profession {
    /**
     * Diplom.
     */
    private String diplom;

    /**
     * Конструктор класса.
     *
     * @param name имя.
     * @param age  возрост.
     * @param diplom специальность учителя.
     */
    public Teacher(String name, int age, String diplom) {
        super(name, age);
        this.diplom = diplom;
    }

    /**
     * Обучение.
     * @param student - ученик.
     * @return - обучение.
     */
    public String teaches(Profession student) {
        return "Преподователь " + this.getName() + " обучает " + student.getName() + " курсу " + diplom;
    }
}
