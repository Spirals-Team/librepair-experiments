package ru.job4j.profession;

/**
 * Diploma.
 */
class Diploma { }
/**
 * Date.
 */
class Date { }
/**
 * Profession.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class Profession {
    /**
     * Name.
     */
    private String name;
    /**
     * Diploma.
     */
    private Diploma diploma;
    /**
     * Date of birth.
     */
    private Date dateOfBirth;
    /**
     * Constructor with parameters.
     * @param name - name
     * @param diploma - diploma
     * @param dateOfBirth - date of birth
     */
    Profession(String name, Diploma diploma, Date dateOfBirth) {
        this.name = name;
        this.diploma = diploma;
        this.dateOfBirth = dateOfBirth;
    }
    /**
     * Get name.
     * @return - name
     */
    public String getName() {
        return this.name;
    }
    /**
     * Get diploma.
     * @return - diploma
     */
    public Diploma getDiploma() {
        return this.diploma;
    }
    /**
     * Get date of birth.
     * @return date of birth
     */
    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }
}
