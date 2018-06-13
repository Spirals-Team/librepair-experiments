package profession;

/**
 * Метод teachesStudents возвращает строку "Доктор Василий лечит пациента Григория",
 * где "Василий" это поле "name" из класса Profession, а "Григория" - аргумент класса Patient.
 *
 * @author Alexandar Vysotskiy
 * @version 1.0
 */

public class Doctor extends Profession {
    public String treatPatient() {
        Patient patient = new Patient();
        Profession doctor = new Profession();
        doctor.setName("Василий");
        doctor.setProfession("Доктор");
        patient.namePatient = "Григория";
        return doctor.getProfession() + " " + doctor.getName() + " лечит пациента " + patient.namePatient;
    }
}