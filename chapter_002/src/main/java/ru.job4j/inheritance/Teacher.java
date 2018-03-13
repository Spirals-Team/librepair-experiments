package ru.job4j.inheritance;

/**
 * teacher.
 */
public class Teacher extends Profession {

    /**
     * teach.
     * @param student param.
     * @return message.
     */
    public String teach(Student student) {

        student.setGoodKnowlige(true);

        return "Учитель " + super.getHuman().getName() + " обучил студента: " + student.getHuman().getName();
    }
}
