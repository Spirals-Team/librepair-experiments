package dev.repositories.classe;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import dev.entites.Classe;

@Service
// @ImportResource("jdd/jdd-Classe.xml")
public class ClasseRepositoryMemoire implements ClasseRepository {

	private List<Classe> classes = new ArrayList();

	/*
	 * public ClasseRepositoryMemoire(List<Classe> classes) { super(); this.classes
	 * = classes; }
	 */

	@PostConstruct
	public void initialiser() {
		// TODO créer un contexte avec la classe ClassPathXmlApplicationContext
		try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdd/jdd-Classe.xml")) {
			// TODO Récupérer les données via la méthode
			// context.getBeansOfType(...)
			Classe classe1 = context.getBean("c1", Classe.class);
			Classe classe2 = context.getBean("c2", Classe.class);
			Classe classe3 = context.getBean("c3", Classe.class);

			classe1.setId(1L);
			classe1.setId(2L);
			classe1.setId(3L);

			classes.add(classe1);
			classes.add(classe2);
			classes.add(classe3);
			// TODO fermer le contexte
		}

	}

	@Override
	public List<Classe> findAll() {
		// TODO Auto-generated method stub
		return classes;
	}

	@Override
	public void save(Classe classe) {
		// TODO Auto-generated method stub
		classes.add(classe);

	}

	@Override
	public void update(Classe classeAvecId) {
		// TODO Auto-generated method stub
		Classe classe = null;
		for (Classe c : classes) {
			if (classeAvecId.getId() == c.getId()) {
				classe = c;
			}
		}

		if (classe != null) {
			classe.setNom(classeAvecId.getNom());
			classe.setStagiaires(classeAvecId.getStagiaires());
		}
	}

	@Override
	public void delete(Classe classe) {
		// TODO Auto-generated method stub
		int i = 0;
		for (Classe c : classes) {
			if (classe.getId() == c.getId()) {
				classes.remove(i);
			}
			i++;
		}

	}

}
