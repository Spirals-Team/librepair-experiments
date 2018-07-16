package dev.entites;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dev.entites.Quizz;

public class JddQuizzTest {

	private ClassPathXmlApplicationContext context;

	@Before
	public void onSetup() {
		this.context = new ClassPathXmlApplicationContext("jdd/jdd-quizz.xml");
	}

	@Test
	public void test_ENTITE_1() {
		Quizz animauxQuizz = context.getBean("animaux", Quizz.class);
		assertThat(animauxQuizz.getTitre()).isEqualTo("Animaux");
		assertThat(animauxQuizz.getQuestions().get(0).getTitre()).isEqualTo("Quel animal court le plus vite?");
	}

	@Test
	public void test_ENTITE_2() {
		Quizz animauxQuizz = context.getBean("geo", Quizz.class);
		assertThat(animauxQuizz.getTitre()).isEqualTo("Géographie");
		assertThat(animauxQuizz.getQuestions().get(0).getTitre()).isEqualTo("Quel est la capital du Mexique?");
	}

	@Test
	public void test_ENTITE_3() {
		Quizz animauxQuizz = context.getBean("math", Quizz.class);
		assertThat(animauxQuizz.getTitre()).isEqualTo("Mathématiques");
		assertThat(animauxQuizz.getQuestions().get(0).getTitre()).isEqualTo("Combien font 2+2?");
	}

	@After
	public void onExit() {
		context.close();
	}

}