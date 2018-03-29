package ru.job4j.inheritance;

public class Teacher extends Profession {
    private String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String tellAboutMyself() {
        return "I teach " + this.subject;
    }
}
