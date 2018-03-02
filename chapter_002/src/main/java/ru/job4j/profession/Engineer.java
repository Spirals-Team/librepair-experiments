package ru.job4j.profession;

/**
 * Specification.
 */
class Specification {
    /**
     * Specification.
     */
    private String spec;
    /**
     * Get specification.
     * @return specification
     */
    String getSpecification() {
        return this.spec;
    }
    /**
     * Constructor.
     * @param spec - specification
     */
    Specification(String spec) {
        this.spec = spec;
    }
}
/**
 * Project.
 */
class Project {
    /**
     * Project.
     */
    private String project;
    /**
     * Get project.
     * @return project
     */
    String getProject() {
        return this.project;
    }
    /**
     * Constructor with parameters.
     * @param specification - specification
     * @param engineer - engineer
     */
    Project(Specification specification, Engineer engineer) {
        this.project = "Engineer " + engineer.getName() + " designed " + specification.getSpecification();
    }
}
/**
 * Engineer.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class Engineer extends Profession {
    /**
     * Constructor with parameters.
     * @param name - name
     * @param diploma - diploma
     * @param dateOfBirth - date of birth
     */
    public Engineer(String name, Diploma diploma, Date dateOfBirth) {
        super(name, diploma, dateOfBirth);
    }
    /**
     * Design.
     * @param specification - specification.
     * @return project
     */
    public Project design(Specification specification) {
        return new Project(specification, this);
    }
}
