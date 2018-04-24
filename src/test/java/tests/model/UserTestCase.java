package tests.model;

import static org.junit.Assert.*;

import org.junit.Test;

import builders.UserBuilder;
import model.User;
import model.exceptions.NameTooShortException;
import model.exceptions.NameTooLongException;
import model.exceptions.*;


public class UserTestCase {
	
	/*
	 * Name Validation Tests.
	 */
	@Test
	public void shouldMakeACorrectNameUserWithoutProblems(){
		User user= UserBuilder.anUser().build();
		assertNotNull(user);
	}
	
	@Test(expected= NameTooShortException.class)
	public void shouldThrowAnExceptionWhenTheNameIsTooShort(){
		User user= UserBuilder.anUser().withNameAndSurname("ab", "c").build();
	}
	
	@Test(expected= NameTooLongException.class)
	public void shouldThrowAnExceptionWhenTheNameIsTooLong(){
		UserBuilder.anUser()
					.withNameAndSurname("Soy una persona con un nombre muy largo", "y de apellido recontralargo tambien")
					.build();
	}
	
	@Test(expected=InvalidEmailException.class)
	public void shouldThrowAnExceptionWhenTheEmailHaveNotGotArroba(){
		UserBuilder.anUser()
					.withEmail("abc")
					.build();
	}

	@Test(expected=InvalidEmailException.class)
	public void shouldThrowAnExceptionWhenTheEmailHaveNotGotPointAfterArroba(){
		UserBuilder.anUser()
					.withEmail("abc@lala")
					.build();
	}
	
	@Test(expected=NoAddressException.class)
	public void shouldThrowAnExceptionWhenHaveNotGotAddress(){
		UserBuilder.anUser()
				.withAddress("")
				.build();
	}
	
	@Test(expected=NoAddressException.class)
	public void shouldThrowAnExceptionWhenHaveNotAddress(){
		UserBuilder.anUser()
				.withAddress(null)
				.build();
	}
	
	
	/*
	 * Reputation Tests.
	 */
	@Test
	public void shouldReputationThrows3WhenNewUser(){
		User user= UserBuilder.anUser().build();
		assertEquals(user.reputation(),3.0,0);
	}
	
	@Test
	public void shouldReputationThrows4WhenAVGIs4(){
		User user= UserBuilder.anUser().build();
		user.processScore(5);
		user.processScore(3);
		assertEquals(user.reputation(),4.0,0);
	}
	
	@Test(expected=NoSuchFieldError.class)
	public void shouldThrowAnExceptionWhenIPutAnOutOfRangeScore(){
		User user= UserBuilder.anUser().build();
		user.processScore(6);
	}
	
	
	/**
	 * Status test.
	 */
	@Test
	public void shouldBeDisabledUserWhenTheReputationIsLowerThanEnablingScore(){
		User user= UserBuilder.anUser().build();
		user.processScore(1);
		assertFalse(user.isEnabled());
	}
	
	@Test
	public void shouldBeEnabledUserWhenTheReputationIsHigherOrEqualThanEnablingScore(){
		User user=UserBuilder.anUser().build();
		user.processScore(5);
		assertTrue(user.isEnabled());
	}

	@Test
	public void shouldBeEnabledUserWhenTheReputationIsLowerThanEnablingScore(){
		User user= UserBuilder.anUser().build();
		user.processScore(1);
		assertFalse(user.isEnabled());
	}
}
