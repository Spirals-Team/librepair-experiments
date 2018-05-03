package ru.job4j.prof;
/**
 * тестирование класса учитель
 *учитель преподаёт студенту урок, и интелект студента выростает + 5
 * студент пытается сдать экзамен, если знания студента позволяют то экзамен будет сдан
 * в противном случае в зачётную книжку выставится незачёт
 */

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import ru.job4j.prof.dopclass.*;

public class TeacherTest {

    @Test
    public void testirovanieTeacherclass() {
        Student vitalka = new Student("Виталька", Sex.М, 18);
        Teacher valentina = new Teacher("Валентина Геннадивна", Sex.Ж, 9, Education.ДОКТОРАНТУРА, Subject.МАТЕМАТИКА);
        valentina.teaches(vitalka);
        assertThat(vitalka.getIntelect(), is(75));
    }
    @Test
    public void testirovanieTeacherclassExzam() {
        Student vitalka = new Student("Виталька", Sex.М, 18);
        Teacher valentina = new Teacher("Валентина Геннадивна", Sex.Ж, 9, Education.ДОКТОРАНТУРА, Subject.МАТЕМАТИКА);
        valentina.studentExamennnation(vitalka);
        Knijka expected = Knijka.НЕЗАЧЁТ;
        assertThat(vitalka.getKnijka(), is(expected));

    }
}

