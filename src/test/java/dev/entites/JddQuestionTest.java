package dev.entites;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JddQuestionTest {

	private ClassPathXmlApplicationContext context;

	@Before
	public void onSetup() {
		context = new ClassPathXmlApplicationContext("jdd/jdd-question.xml");

	}

	@Test
	public void test_OptionQuestion_1() {
		// TODO
		OptionQuestion rep1 = context.getBean("reponse11", OptionQuestion.class);
		assertTrue(rep1.getLibelle().equals("1"));
	}

	@Test
	public void test_OptionQuestion_2() {
		// TODO
		OptionQuestion rep2 = context.getBean("reponse12", OptionQuestion.class);
		assertTrue(rep2.getLibelle().equals("2"));
	}

	@Test
	public void test_OptionQuestion_3() {
		// TODO
		OptionQuestion rep3 = context.getBean("reponse13", OptionQuestion.class);
		assertTrue(rep3.getLibelle().equals("3"));
	}

	@Test
	public void test_Question() {
		// TODO
		Question quizz = context.getBean("question1", Question.class);
		assertTrue(quizz.getTitre().equals("Quelle réponse est égale à deux ?"));

	}

	@After
	public void onExit() {
		context.close();
	}

}