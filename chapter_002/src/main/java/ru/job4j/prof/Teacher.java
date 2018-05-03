package ru.job4j.prof;

import ru.job4j.prof.dopclass.*;

public class Teacher extends Profession {
    private Subject subject;

    public Subject getSubject() {
        return subject;
    }

    public Teacher(String name, Sex sex, int experience, Education education, Subject subject) {
        super(name, sex, experience, education);
        this.subject = subject;
    }

    public String teaches(Student student) {
        String result = "";
        student.setIntelect(5);
        result = "Учитель преподал урок студенту, он получил знания + 5, когда Intelect студента достигнет 100 тогда можно сдать экзамент, Интелект студента  = " + student.getIntelect();
        System.out.println(result);
        return result;
    }

    public String studentExamennnation(Student student) {
        String result = "";
        if (student.getIntelect() < 100) {
            student.setKnijka(false);
            result = "студенту необходимо пройти обучение " + student.getKnijka();
            System.out.println(result);
        } else {
            student.setKnijka(true);
            result = "студент сдал экзамент " + student.getKnijka();
            System.out.println(result);
        }
        return result;
    }
}


