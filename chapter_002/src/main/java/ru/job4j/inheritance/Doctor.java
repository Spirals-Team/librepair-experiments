package ru.job4j.inheritance;

public class Doctor extends Profession {
    private String specialisation;
    private String position;

    public String healEngineer(Engineer engineer) {
        return "Doctor " + this.getName() + " heals " + engineer.getName();
    }

    public String healTeacher(Teacher teacher) {
        return "Doctor " + this.getName() + " heals " + teacher.getName();
    }

}
