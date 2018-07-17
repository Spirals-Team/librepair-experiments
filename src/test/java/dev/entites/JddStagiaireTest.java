package dev.entites;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JddStagiaireTest {

	private ClassPathXmlApplicationContext context;

	@Before
	public void onSetup() {
		context = new ClassPathXmlApplicationContext("jdd/jdd-stagiaire.xml");

	}

	@Test
	public void test_STAGIAIRE_1() {
		Stagiaire stagiaire1 = context.getBean("stagiaire1", Stagiaire.class);

		assertThat(stagiaire1.getPrenom()).isEqualTo("Alain");
		assertThat(stagiaire1.getNom()).isEqualTo("Dupont");
		assertThat(stagiaire1.getEmail()).isEqualTo("alain.dupont@gmail.com");
		assertThat(stagiaire1.getPhotoUrl()).isNotEmpty();

	}

	@Test
	public void test_STAGIAIRE_2() {
		Stagiaire stagiaire2 = context.getBean("stagiaire2", Stagiaire.class);
		assertThat(stagiaire2.getPrenom()).isEqualTo("Marine");
		assertThat(stagiaire2.getNom()).isEqualTo("Martin");
		assertThat(stagiaire2.getEmail()).isEqualTo("marine.matin@gmail.com");
		assertThat(stagiaire2.getPhotoUrl()).isNotEmpty();
	}

	@Test
	public void test_STAGIAIRE_3() {
		Stagiaire stagiaire3 = context.getBean("stagiaire3", Stagiaire.class);
		assertThat(stagiaire3.getPrenom()).isEqualTo("Françoise");
		assertThat(stagiaire3.getNom()).isEqualTo("Mars");
		assertThat(stagiaire3.getEmail()).isEqualTo("francoise.mars@gmail.com");
		assertThat(stagiaire3.getPhotoUrl()).isNotEmpty();
	}

	@After
	public void onExit() {
		context.close();
	}

}
