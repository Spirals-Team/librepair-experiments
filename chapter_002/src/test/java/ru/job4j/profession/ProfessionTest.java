package ru.job4j.profession;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
/**
 * Profession.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class ProfessionTest {
    /**
     * Doctor heal patient.
     */
    @Test
    public void whenDoctorHealPatientThenDiagnose() {
        Doctor doctor = new Doctor("Mikael", new Diploma(), new Date());
        String result = doctor.heal(new Patient("Ivan")).getDiagnose();
        String expect = "Doctor Mikael heals Ivan";
        assertThat(result, is(expect));
    }
    /**
     * Teacher teach student.
     */
    @Test
    public void whenTeacherTeachStudentThenKnowledge() {
        Teacher teacher = new Teacher("Mikael", new Diploma(), new Date());
        String result = teacher.teach(new Student("Ivan")).getKnowledge();
        String expect = "Teacher Mikael teaches Ivan";
        assertThat(result, is(expect));
    }
    /**
     * Engineer is designed to specification.
     */
    @Test
    public void whenEngineerDesignedToSpecificationThenProject() {
        Engineer engineer = new Engineer("Mikael", new Diploma(), new Date());
        String result = engineer.design(new Specification("bridge")).getProject();
        String expect = "Engineer Mikael designed bridge";
        assertThat(result, is(expect));
    }
}
