package dev.entites;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dev.entites.ProfilUtilisateur;
import dev.entites.Utilisateur;

public class JddUtilisateurTest {

	private ClassPathXmlApplicationContext context;

	@Before
	public void onSetup() {
		this.context = new ClassPathXmlApplicationContext("jdd/jdd-utilisateur.xml");
	}

	@Test
	public void test_ENTITE_1() {
		Utilisateur user1 = context.getBean("user1", Utilisateur.class);
		assertThat(user1.getIdentifiant()).isEqualTo("00000");
		assertThat(user1.getMotDePasse()).isEqualTo("00000");
		assertThat(user1.getProfil()).isEqualTo(ProfilUtilisateur.STAGIAIRE);
	}

	@Test
	public void test_ENTITE_2() {
		Utilisateur user2 = context.getBean("user2", Utilisateur.class);
		assertThat(user2.getIdentifiant()).isEqualTo("11111");
		assertThat(user2.getMotDePasse()).isEqualTo("11111");
		assertThat(user2.getProfil()).isEqualTo(ProfilUtilisateur.STAGIAIRE);
	}

	@Test
	public void test_ENTITE_3() {
		Utilisateur user3 = context.getBean("user3", Utilisateur.class);
		assertThat(user3.getIdentifiant()).isEqualTo("22222");
		assertThat(user3.getMotDePasse()).isEqualTo("22222");
		assertThat(user3.getProfil()).isEqualTo(ProfilUtilisateur.STAGIAIRE);
	}

	@After
	public void onExit() {
		context.close();
	}

}