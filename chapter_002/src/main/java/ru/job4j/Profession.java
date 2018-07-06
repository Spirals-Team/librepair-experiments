package ru.job4j;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class Profession {
    private String name;
    private Diploma diploma;

    public Profession(String name, Diploma diploma) {
        this.name = name;
        this.diploma = diploma;
    }

    public String getName() {
        return name;
    }
}

class Teacher extends Profession {
    private String subject;

    public Teacher(String name, Diploma diploma) {
        super(name, diploma);
        this.subject = diploma.getSubject();
    }

    public Knowledge teach(Student student, double k) {
        System.out.println("Учитель " + this.getName() + " учит студента с иминем " + student.getName() + " предмету " + this.subject);
        System.out.println("Студен получил знание ");
        student.getPercent().high(k);
        return student.getPercent();
    }
}

class Student {
    private String name;
    private Knowledge percent;

    public Student(String name, Knowledge percent) {
        this.name = name;
        this.percent = percent;
    }

    public String getName() {
        return name;
    }

    public Knowledge getPercent() {
        return percent;
    }
}

class Diploma {
    private String nameOfUniversity;
    private String subject;
    private int year;

    public Diploma(String almaMatter, String subject, int year) {
        nameOfUniversity = almaMatter;
        this.subject = subject;
        this.year = year;
    }

    public String getSubject() {
        return subject;
    }
}

class Knowledge {
    private double percentOfKnowledge;

    public Knowledge(double percentOfKnowledge) {
        this.percentOfKnowledge = percentOfKnowledge;
    }

    public void high(double k) {
        if (percentOfKnowledge >= 100) {
            System.out.println("Предмет пройден");
        } else {
            percentOfKnowledge += k;
            System.out.println("Усвоено предета " + percentOfKnowledge + "%");
        }
    }

    public double getPercentOfKnowledge() {
        return percentOfKnowledge;
    }
}
