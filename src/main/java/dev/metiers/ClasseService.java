package dev.metiers;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.entites.Classe;
import dev.repositories.classe.ClasseRepository;

@Service
public class ClasseService {

	private ClasseRepository classeRepository;

	public ClasseService(ClasseRepository classeRepository) {
		super();
		this.classeRepository = classeRepository;
	}

	public List<Classe> lister() {
		return classeRepository.findAll();
	}

	public void ajouter(Classe classe) {
		classeRepository.save(classe);
	}

	public void maj(Classe classe) {
		classeRepository.update(classe);
	}

	public void supprimer(Classe classe) {
		classeRepository.delete(classe);
	}

	public Classe trouverClasseParId(Long id) {
		for (Classe c : lister()) {
			if (c.getId().equals(id)) {
				return c;
			}
		}
		return null;
	}

}
