/**
 *
 */
package dev.entites;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author mtremion
 *
 */
public class JddClasseTest {

	private ClassPathXmlApplicationContext context;

	@Before
	public void onSetup() {
		context = new ClassPathXmlApplicationContext("jdd/jdd-Classe.xml");

	}

	@Test
	public void test_ENTITE_1() {
		Classe classe1 = context.getBean("c1", Classe.class);

		assertThat(classe1.getNom()).isEqualTo("D11");
		assertThat(classe1.getStagiaires()).isNotEmpty();
		assertThat(classe1.getStagiaires().size()).isEqualTo(2);
		assertThat(classe1.getStagiaires().get(0).getPrenom()).isEqualTo("Maxime");
		assertThat(classe1.getStagiaires().get(1).getPrenom()).isEqualTo("Anna");
		// TODO Ajouter des assertions
	}

	@Test
	public void test_ENTITE_2() {
		// TODO
		Classe classe2 = context.getBean("c2", Classe.class);

		assertThat(classe2.getNom()).isEqualTo("D12");
		assertThat(classe2.getStagiaires()).isNotEmpty();
		assertThat(classe2.getStagiaires().size()).isEqualTo(2);
		assertThat(classe2.getStagiaires().get(0).getPrenom()).isEqualTo("Maxime");
		assertThat(classe2.getStagiaires().get(1).getPrenom()).isEqualTo("Melissa");
	}

	@Test
	public void test_ENTITE_3() {
		// TODO
		Classe classe3 = context.getBean("c3", Classe.class);

		assertThat(classe3.getNom()).isEqualTo("D13");
		assertThat(classe3.getStagiaires()).isNotEmpty();
		assertThat(classe3.getStagiaires().size()).isEqualTo(3);
		assertThat(classe3.getStagiaires().get(0).getPrenom()).isEqualTo("Maxime");
		assertThat(classe3.getStagiaires().get(1).getPrenom()).isEqualTo("Anna");
		assertThat(classe3.getStagiaires().get(2).getPrenom()).isEqualTo("Melissa");
	}

	@After
	public void onExit() {
		context.close();
	}

}