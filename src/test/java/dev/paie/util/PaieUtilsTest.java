package dev.paie.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PaieUtilsTest {
	private PaieUtils paieUtils;
	private ClassPathXmlApplicationContext context;

	@Before
	public void onSetup() {
		context = new ClassPathXmlApplicationContext("app-config.xml");
		paieUtils = context.getBean(PaieUtils.class);
	}

	@Test
	public void test_formaterBigDecimal_entier_positif() {

		String resultat = paieUtils.formaterBigDecimal(new BigDecimal("2"));
		assertThat(resultat, equalTo("2.00"));
	}

	@Test
	public void test_formaterBigDecimal_trois_chiffres_apres_la_virgule() {

		String resultat = paieUtils.formaterBigDecimal(new BigDecimal("2.199"));
		assertThat(resultat, equalTo("2.20"));
	}

	@Test
	public void test_addition_BigDecimal() {
		BigDecimal big1 = new BigDecimal("2.22");
		BigDecimal big2 = new BigDecimal("4.76");

		String resultat = paieUtils.formaterBigDecimal(big1.add(big2));
		assertThat(resultat, equalTo("6.98"));
	}

	@After
	public void onExit() {
		context.close();
	}
}