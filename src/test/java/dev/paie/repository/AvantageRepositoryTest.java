package dev.paie.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.paie.config.H2Config;
import dev.paie.config.JeuxDeDonneesConfig;
import dev.paie.config.JpaConfig;
import dev.paie.config.ServicesConfig;
import dev.paie.entite.Avantage;

//TODO compléter la configuration
//Sélection des classes de configuration Spring à utiliser lors du test
@ContextConfiguration(classes = {ServicesConfig.class ,H2Config.class }) 
//Configuration JUnit pour que Spring prenne la main sur le cycle de vie du test
@RunWith(SpringRunner.class)
public class AvantageRepositoryTest {

 @Autowired private AvantageRepository avantageRepository;

  private Avantage avantage;
 
 @Test
 public void test_sauvegarder_lister_mettre_a_jour() {
     // TODO sauvegarder un nouvel avantage
	 avantage =new Avantage(1,"code", "nom", new BigDecimal("2.20"));
     avantageRepository.save(avantage);
	 // TODO vérifier qu'il est possible de récupérer le nouvel avantage via la méthode findOne
	 assertThat(avantageRepository.findAll().contains(avantage)) ;
     // TODO modifier un avantage
	 avantage.setCode("test");
	 avantageRepository.save(avantage);
     // TODO vérifier que les modifications sont bien prises en compte via la méthode findOne
	 assertThat(avantageRepository.findByCode("test"));
 }
}