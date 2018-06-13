package profession;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ProfessionTest {
    @Test
    public void professionDoctor() {
        Doctor doc = new Doctor();
        String result = doc.treatPatient();
        assertThat(result, is("Доктор Василий лечит пациента Григория"));
    }

    @Test
    public void professionTeacher() {
        Teacher teacher = new Teacher();
        String result = teacher.teachesStudents();
        assertThat(result, is("Преподаватель Ольга Ивановна учит студента Андреева двум дисциплинам"));
    }

    @Test
    public void professionEnginneer() {
        Engineer engineer = new Engineer();
        String result = engineer.buildHouse();
        assertThat(result, is("Инженер Иванов строит дом высотой 20 этажей"));
    }
}
