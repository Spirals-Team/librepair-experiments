package ru.job4j.profession;

/**
 * Student.
 */
class Student {
    /**
     *  Name of student.
     */
    private String name;
    /**
     * Get name.
     * @return name
     */
    String getName() {
        return this.name;
    }
    /**
     * Constructor.
     * @param name - name
     */
    Student(String name) {
        this.name = name;
    }
}
/**
 * Knowledge.
 */
class Knowledge {
    /**
     * Knowledge.
     */
    private String knowledge;
    /**
     * Get knowledge.
     * @return knowledge
     */
    String getKnowledge() {
        return this.knowledge;
    }
    /**
     * Constructor with parameters.
     * @param student - student
     * @param teacher - teacher
     */
    Knowledge(Student student, Teacher teacher) {
        this.knowledge = "Teacher " + teacher.getName() + " teaches " + student.getName();
    }
}
/**
 * Teacher.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class Teacher extends Profession {
    /**
     * Constructor with parameters.
     * @param name - name
     * @param diploma - diploma
     * @param dateOfBirth - date of birth
     */
    public Teacher(String name, Diploma diploma, Date dateOfBirth) {
        super(name, diploma, dateOfBirth);
    }
    /**
     * Teach the student.
     * @param student - student
     * @return knowledge
     */
    public Knowledge teach(Student student) {
        return new Knowledge(student, this);
    }
}
