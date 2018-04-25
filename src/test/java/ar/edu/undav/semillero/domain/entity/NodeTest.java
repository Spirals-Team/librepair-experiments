package ar.edu.undav.semillero.domain.entity;

import org.junit.Assert;
import org.junit.Test;

public class NodeTest {

	@Test
	public void testCreation() {
		String nombre = "nombre";
		String address = "address";
		Node node = new Node(nombre, address);
		Assert.assertEquals(nombre, node.getName());
		Assert.assertEquals(address, node.getAddress());
	}

	@Test
	public void testStudents() {
		Node node = new Node();
		Assert.assertTrue(node.getStudents().isEmpty());

		node.addStudent(new Student());
		Assert.assertEquals(1, node.getStudents().size());
	}

}
