package dev.repositories.classe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import dev.entites.Classe;

public class ClasseRepositoryMemoire implements ClasseRepository {

	private List<Classe> classes = new ArrayList<>();

	@PostConstruct
	public void initialiser() {
		try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdd/jdd-Classe.xml")) {
			Classe classe1 = context.getBean("c1", Classe.class);
			Classe classe2 = context.getBean("c2", Classe.class);
			Classe classe3 = context.getBean("c3", Classe.class);

			classe1.setId(1L);
			classe2.setId(2L);
			classe3.setId(3L);

			classes.add(classe1);
			classes.add(classe2);
			classes.add(classe3);

		}

	}

	@Override
	public List<Classe> findAll() {

		return classes;
	}

	@Override
	public void save(Classe classe) {

		classes.add(classe);

	}

	@Override
	public void update(Classe classeAvecId) {

		Long id = classeAvecId.getId();
		for (Classe c : classes) {
			if (id.equals(c.getId())) {
				c.setNom(classeAvecId.getNom());
				c.setStagiaires(classeAvecId.getStagiaires());
			}
		}

	}

	@Override
	public void delete(Classe classe) {

		int i = 0;
		int index = 0;
		for (Classe c : classes) {
			if (classe.getId().equals(c.getId())) {
				index = i;
			}
			i++;
		}
		classes.remove(index);
	}

	@Override
	public Optional<Classe> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
