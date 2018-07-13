package dev.entites;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JddExamensTest {
	private ClassPathXmlApplicationContext context;

	@Before
	public void onSetup() {
		context = new ClassPathXmlApplicationContext("jdd/jdd-examens.xml");

	}

	@Ignore
	@Test
	public void test_ENTITE_1() {
		Examen entite1 = context.getBean("exam1", Examen.class);

		assertTrue(entite1.getTitre().equals("Java EE"));
		assertTrue(entite1.getNotes().get(0).getNoteSur20() == 12);
		assertTrue(entite1.getClasse().getStagiaires().get(1).getEmail().equals("rourou@gmail.com"));

	}

	@Ignore
	@Test
	public void test_ENTITE_2() {

		Examen entite2 = context.getBean("exam2", Examen.class);

		assertTrue(entite2.getTitre().equals("Le feu !"));
		assertTrue(entite2.getQuizz().getQuestions().get(0).getTitre().equals("Le bois brule t-il ?"));
		assertTrue((entite2.getQuizz().getQuestions().get(1).getOptions().get(0).getLibelle().equals("foo")));

	}

	@Ignore
	@Test
	public void test_ENTITE_3() {
		// TODO
	}

	@After
	public void onExit() {
		context.close();
	}
}
