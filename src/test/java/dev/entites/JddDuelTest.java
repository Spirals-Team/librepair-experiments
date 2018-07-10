package dev.entites;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JddDuelTest {

	private ClassPathXmlApplicationContext context;

	@Before
	public void onSetup() {
		context = new ClassPathXmlApplicationContext("jdd/jdd-Duel.xml");

	}

	@Test
	public void testDuel1() {
		Duel duel1 = context.getBean("duel1", Duel.class);
		assertThat(duel1.getQuizz().getTitre()).isEqualTo("Quizz 1");
		assertThat(duel1.getStagiaireA().getNom()).isEqualTo("Abitbol");
		assertThat(duel1.getStagiaireA().getPrenom()).isEqualTo("Georges");
		assertThat(duel1.getStagiaireB().getNom()).isEqualTo("Mansoif");
		assertThat(duel1.getStagiaireB().getPrenom()).isEqualTo("Gérard");
	}

	@Test
	public void testDuel2() {
		Duel duel2 = context.getBean("duel2", Duel.class);
		assertThat(duel2.getQuizz().getTitre()).isEqualTo("Quizz 2");
		assertThat(duel2.getStagiaireA().getNom()).isEqualTo("Mansoif");
		assertThat(duel2.getStagiaireA().getPrenom()).isEqualTo("Gérard");
		assertThat(duel2.getStagiaireB().getNom()).isEqualTo("Detable");
		assertThat(duel2.getStagiaireB().getPrenom()).isEqualTo("Sylvain");
	}

	@Test
	public void testDuel3() {
		Duel duel3 = context.getBean("duel3", Duel.class);
		assertThat(duel3.getQuizz().getTitre()).isEqualTo("Quizz 3");
		assertThat(duel3.getStagiaireA().getNom()).isEqualTo("Abitbol");
		assertThat(duel3.getStagiaireA().getPrenom()).isEqualTo("Georges");
		assertThat(duel3.getStagiaireB().getNom()).isEqualTo("Detable");
		assertThat(duel3.getStagiaireB().getPrenom()).isEqualTo("Sylvain");
	}

	@After
	public void onExit() {
		context.close();
	}

}
