package dev.repositories.classe;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import dev.entites.Classe;

//@Repository
public class ClasseRepositoryJPA implements ClasseRepository {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public List<Classe> findAll() {
		// TODO Auto-generated method stub
		TypedQuery<Classe> query = em.createQuery("SELECT c FROM Classe c", Classe.class);
		List<Classe> classes = query.getResultList();
		return classes;

	}

	@Transactional
	@Override
	public void save(Classe classe) {
		// TODO Auto-generated method stub
		em.persist(classe);

	}

	@Transactional
	@Override
	public void update(Classe classeAvecId) {
		// TODO Auto-generated method stub
		if (classeAvecId != null) {
			em.merge(classeAvecId);
		}

	}

	@Transactional
	@Override
	public void delete(Classe classe) {
		// TODO Auto-generated method stub
		Classe c = em.find(Classe.class, classe.getId());
		if (c != null) {
			em.remove(c);
		}
	}

	@Override
	public Optional<Classe> findById(Long id) {
		// TODO Auto-generated method stub
		Classe c = em.find(Classe.class, id);
		return Optional.ofNullable(c);
	}

}
