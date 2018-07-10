package dev.repositories.duel;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import dev.entites.Duel;

@Service
public class DuelRepositoryMemoire implements DuelRepository {
	private List<Duel> duels = new ArrayList<>();

	/**
	 * Initialiser la liste des duels
	 */
	@PostConstruct
	public void initialiser() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdd/jdd-Duel.xml");
		duels = new ArrayList<>(context.getBeansOfType(Duel.class).values());
		context.close();
	}

	@Override
	public List<Duel> findAll() {
		return duels;
	}

	@Override
	public void save(Duel duel) {
		duels.add(duel);
	}

	@Override
	public void update(Duel duelAvecId) {
		for (Duel duel : duels)
			if (duel.getId().equals(duelAvecId.getId()))
				duels.set(duels.indexOf(duel), duelAvecId);
	}

	@Override
	public void delete(Duel duel) {
		duels.remove(duels.indexOf(duel));
	}

}
