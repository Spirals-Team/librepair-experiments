package dev.repositories;

import static org.junit.Assert.assertTrue;

import dev.entites.ProfilUtilisateur;
import dev.entites.Utilisateur;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.repositories.utilisateur.UtilisateurBaseRepositoryMemoire;

//Sélection des classes de configuration Spring à utiliser lors du test
@ContextConfiguration(classes = { UtilisateurBaseRepositoryMemoire.class })
// Configuration JUnit pour que Spring prenne la main sur le cycle de vie du
// test
@RunWith(SpringRunner.class)
public class UtilisateurRepositoryMemoireTest {

	@Autowired
	private UtilisateurBaseRepositoryMemoire utilisateurService;

	@Test
	public void test_ENTITE_1() {

		Utilisateur utilTest = (Utilisateur) utilisateurService.findAll().get(1);

		assertTrue(utilTest.getIdentifiant().equals("11111"));
		assertTrue(utilTest.getMotDePasse().equals("11111"));
		assertTrue(utilTest.getProfil().equals(ProfilUtilisateur.STAGIAIRE));

	}

}
