package ru.job4j.profession;

/**
 * Patient.
 */
class Patient {
    /**
     *  Name of patient.
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
    Patient(String name) {
        this.name = name;
    }
}
/**
 * Diagnose.
 */
class Diagnose {
    /**
     * Diagnose.
     */
    private String diagnose;
    /**
     * Get diagnose.
     * @return diagnose
     */
    String getDiagnose() {
        return this.diagnose;
    }
    /**
     * Constructor with parameters.
     * @param patient - patient
     * @param doctor - doctor
     */
    Diagnose(Patient patient, Doctor doctor) {
        this.diagnose = "Doctor " + doctor.getName() + " heals " + patient.getName();
    }
}
/**
 * Doctor.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class Doctor extends Profession {
    /**
     * Constructor with parameters.
     * @param name - name
     * @param diploma - diploma
     * @param dateOfBirth - date of birth
     */
    public Doctor(String name, Diploma diploma, Date dateOfBirth) {
        super(name, diploma, dateOfBirth);
    }
    /**
     * Heal the patient.
     * @param patient - patient
     * @return - diagnose
     */
    public Diagnose heal(Patient patient) {
        return new Diagnose(patient, this);
    }
}
