package org.iths.mimicTests.sprint1;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestMockTest {
	
	//Example test with test
	@Test
	public void test() {
		MockTest mock = new MockTest();
		mock.storeQuestionAndAnswer("1+1", "2");
		String answer = mock.checkQuestion("1+1");
		assertEquals("2",answer);
	}

}
