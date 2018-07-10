package dev.entites;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JddConcoursTest {

	private ClassPathXmlApplicationContext context;

	@Before
	public void onSetup() {
		context = new ClassPathXmlApplicationContext("jdd/jdd-concours.xml");

	}

	@Test
	public void test_ENTITE_1() {
		Concours entite1 = context.getBean("LeGdConcour", Concours.class);
		assertThat(entite1.getParticipants()).isNotEmpty();

	}

	@Test
	public void test_ENTITE_2() {
		// TODO
		Concours entite1 = context.getBean("LeGdConcour", Concours.class);
		assertThat(entite1.getParticipants().get(1).getNom()).isEqualTo("Ntmb");
	}

	@Test
	public void test_ENTITE_3() {
		// TODO
		Concours entite1 = context.getBean("LeGdConcour", Concours.class);
		assertThat(entite1.getQuizzes().get(0).getTitre()).isEqualTo("le carre rouge");
	}

	@After
	public void onExit() {
		context.close();
	}

}
