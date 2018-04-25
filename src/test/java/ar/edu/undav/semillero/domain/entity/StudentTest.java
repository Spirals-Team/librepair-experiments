package ar.edu.undav.semillero.domain.entity;

import org.junit.Assert;
import org.junit.Test;

public class StudentTest {

	@Test
	public void testCreation() {
		String nombre = "nombre";
		Node node = new Node();
        Student student = new Student(nombre, node); // Actualizar
																		// Assert
		Assert.assertEquals(nombre, student.getName());
		Assert.assertEquals(false, student.isDeleted());
	}

	@Test
	public void testAddInterview() {
		Student student = new Student();
		Assert.assertTrue(student.getInterviews().isEmpty());

		student.addInterview(new Interview());
		Assert.assertEquals(1, student.getInterviews().size());
	}
}
