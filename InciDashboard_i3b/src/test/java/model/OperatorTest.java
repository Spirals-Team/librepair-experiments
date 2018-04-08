package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class OperatorTest {

	@Test
	public void testOperator() {
		Operator op = new Operator("operatorId", "password");
		assertEquals("operatorId", op.getOperatorId());
		assertEquals("password", op.getPassword());
		op.setOperatorId("a");
		op.setPassword("b");
		assertEquals("a", op.getOperatorId());
		assertEquals("b", op.getPassword());
	}	

}
