package dev.repositories.sondage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import dev.entites.Sondage;

public class SondageRepositoryMemoire implements SondageRepository {

	private List<Sondage> sondages = new ArrayList<>();

	@PostConstruct
	public void initialiser() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdd/jdd-SONDAGE.xml");
		Map<String, Sondage> map = context.getBeansOfType(Sondage.class);
		for (Entry<String, Sondage> m : map.entrySet()) {
			sondages.add(m.getValue());
		}
		context.close();
	}

	@Override
	public List<Sondage> findAll() {
		return sondages;
	}

	@Override
	public void save(Sondage sondage) {
		sondages.add(sondage);
	}

	@Override
	public void update(Sondage entite) {
		for (Sondage sondage : sondages) {
			if (sondage.getId() == entite.getId()) {
				sondages.set(sondages.indexOf(sondage), entite);
			}

		}
	}

	@Override
	public void delete(Sondage entite) {
		int id = 0;
		for (Sondage s : sondages) {
			if (s.getId() == entite.getId()) {
				id = sondages.indexOf(s);
			}
		}
		sondages.remove(id);
	}

	@Override
	public Optional<Sondage> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
