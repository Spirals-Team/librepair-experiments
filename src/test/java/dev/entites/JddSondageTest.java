package dev.entites;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JddSondageTest {

	ClassPathXmlApplicationContext context;

	@Before
	public void onSetup() {
		context = new ClassPathXmlApplicationContext("jdd/jdd-SONDAGE.xml");

	}

	@Test
	public void test_sondage1() {
		Sondage s = context.getBean("sondage1", Sondage.class);

		assertThat(s.getTitre()).isEqualTo("Titre sondage 1");
		assertThat(s.getClasse().getNom()).isEqualTo("D11");
		assertThat(s.getOptions().get(1).getLibelle()).isEqualTo("option 2");
	}

	@Test
	public void test_sondage2() {
		Sondage s = context.getBean("sondage2", Sondage.class);

		assertThat(s.getTitre()).isEqualTo("Titre sondage 2");
		assertThat(s.getClasse().getNom()).isEqualTo("D12");
		assertThat(s.getOptions().get(2).getLibelle()).isEqualTo("option 3");
	}

	@Test
	public void test_sondage3() {
		Sondage s = context.getBean("sondage3", Sondage.class);

		assertThat(s.getTitre()).isEqualTo("Titre sondage 3");
		assertThat(s.getClasse().getNom()).isEqualTo("D13");
		assertThat(s.getOptions().get(0).getLibelle()).isEqualTo("option 1");
	}

	@After
	public void unSetup() {
		context.close();
	}

}
