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
		assertThat(duel1.getQuizz().getTitre()).isEqualTo("Animaux");
		assertThat(duel1.getStagiaireA().getNom()).isEqualTo("Dupont");
		assertThat(duel1.getStagiaireA().getPrenom()).isEqualTo("Alain");
		assertThat(duel1.getStagiaireB().getNom()).isEqualTo("Martin");
		assertThat(duel1.getStagiaireB().getPrenom()).isEqualTo("Marine");
	}

	@Test
	public void testDuel2() {
		Duel duel2 = context.getBean("duel2", Duel.class);
		assertThat(duel2.getQuizz().getTitre()).isEqualTo("Géographie");
		assertThat(duel2.getStagiaireA().getNom()).isEqualTo("Martin");
		assertThat(duel2.getStagiaireA().getPrenom()).isEqualTo("Marine");
		assertThat(duel2.getStagiaireB().getNom()).isEqualTo("Mars");
		assertThat(duel2.getStagiaireB().getPrenom()).isEqualTo("Françoise");
	}

	@Test
	public void testDuel3() {
		Duel duel3 = context.getBean("duel3", Duel.class);
		assertThat(duel3.getQuizz().getTitre()).isEqualTo("Mathématiques");
		assertThat(duel3.getStagiaireA().getNom()).isEqualTo("Dupont");
		assertThat(duel3.getStagiaireA().getPrenom()).isEqualTo("Alain");
		assertThat(duel3.getStagiaireB().getNom()).isEqualTo("Mars");
		assertThat(duel3.getStagiaireB().getPrenom()).isEqualTo("Françoise");
	}

	@After
	public void onExit() {
		context.close();
	}

}
