package dev.repositories.duel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import dev.entites.Duel;

public class DuelRepositoryMemoire implements DuelRepository {
	private List<Duel> duels = new ArrayList<>();

	/**
	 * Initialiser la liste des duels
	 */
	@PostConstruct
	public void initialiser() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdd/jdd-Duel.xml");
		this.duels = new ArrayList<>(context.getBeansOfType(Duel.class).values());
		context.close();
	}

	@Override
	public List<Duel> findAll() {
		return this.duels;
	}

	@Override
	public void save(Duel duel) {
		this.duels.add(duel);
	}

	@Override
	public void update(Duel duelAvecId) {
		for (Duel duel : this.duels)
			if (duel.getId().equals(duelAvecId.getId()))
				this.duels.set(this.duels.indexOf(duel), duelAvecId);
	}

	@Override
	public void delete(Duel duel) {
		this.duels.remove(this.duels.indexOf(duel));
	}

	@Override
	public Optional<Duel> findById(Long id) {
		return this.duels.stream().filter(s -> s.getId().equals(id)).findFirst();
	}

}
