package com.prussia.test.myspring;
import static org.mockito.Mockito.doNothing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.prussia.play.spring.service.RiskAssessorService;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseAccountServiceTest {

	@Mock
	private RiskAssessorService riskAssessor;

	@Test
	public void testCreateAcctount() {
//		riskAssessor.createAcctount("account11234", "Prussia")
		doNothing().when(riskAssessor).createAcctount("account1234", "Prussia");

	}
}
