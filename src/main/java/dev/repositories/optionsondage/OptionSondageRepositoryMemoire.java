package dev.repositories.optionsondage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import dev.entites.OptionSondage;

public class OptionSondageRepositoryMemoire implements OptionSondageRepository {

	private List<OptionSondage> optionSondages = new ArrayList<>();

	@PostConstruct
	public void initialiser() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdd/jdd-OPTIONSONDAGE.xml");
		Map<String, OptionSondage> map = context.getBeansOfType(OptionSondage.class);
		for (Entry<String, OptionSondage> m : map.entrySet()) {
			optionSondages.add(m.getValue());
		}
		context.close();
	}

	@Override
	public List<OptionSondage> findAll() {
		return optionSondages;
	}

	@Override
	public void save(OptionSondage entite) {
		optionSondages.add(entite);
	}

	@Override
	public void update(OptionSondage entiteAvecId) {
		for (OptionSondage optionSondage : optionSondages) {
			if (optionSondage.getId() == entiteAvecId.getId()) {
				optionSondages.set(optionSondages.indexOf(optionSondage), entiteAvecId);
			}
		}
	}

	@Override
	public void delete(OptionSondage entite) {
		int id = 0;
		for (OptionSondage s : optionSondages) {
			if (s.getId() == entite.getId()) {
				id = optionSondages.indexOf(s);
			}
		}
		optionSondages.remove(id);
	}

	@Override
	public Optional<OptionSondage> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
