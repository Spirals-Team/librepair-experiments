package ru.job4j.inherit;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
* Test Teacher.
* @author Igor Fedorenko (mailto:if.zommy@gmail.com)
* @version $Id$
* @since 0.1
*/
public class TeacherTest {
	/**
	* return string.
	*/
	@Test
	public void whenStudentComeToTeacherThenAnnaTeachSofia() {
		Teacher teacher = new Teacher("Anna");
		Student student = new Student("Sofia");
		String result = teacher.teach(student);
		String expect = "Учитель Anna проводит дополнительное занятие с Sofia";
		assertThat(result, is(expect));
	}
}