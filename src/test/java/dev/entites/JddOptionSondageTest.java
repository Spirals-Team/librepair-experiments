package dev.entites;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JddOptionSondageTest {

	ClassPathXmlApplicationContext context;

	@Before
	public void onSetup() {
		context = new ClassPathXmlApplicationContext("jdd/jdd-OPTIONSONDAGE.xml");

	}

	@Test
	public void test_optionSondage1() {
		OptionSondage optionSondage1 = context.getBean("optionSondage1", OptionSondage.class);

		assertThat(optionSondage1.getLibelle()).isEqualTo("option 1");
		assertThat(optionSondage1.getDescription()).isEqualTo("option1 de sondage");
	}

	@Test
	public void test_optionSondage2() {
		OptionSondage optionSondage1 = context.getBean("optionSondage2", OptionSondage.class);

		assertThat(optionSondage1.getLibelle()).isEqualTo("option 2");
		assertThat(optionSondage1.getDescription()).isEqualTo("option2 de sondage");
	}

	@Test
	public void test_optionSondage3() {
		OptionSondage optionSondage1 = context.getBean("optionSondage3", OptionSondage.class);

		assertThat(optionSondage1.getLibelle()).isEqualTo("option 3");
		assertThat(optionSondage1.getDescription()).isEqualTo("option3 de sondage");
	}

	@After
	public void unSetup() {
		context.close();
	}

}
