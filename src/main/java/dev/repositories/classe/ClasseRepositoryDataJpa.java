package dev.repositories.classe;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dev.entites.Classe;

@Repository
public class ClasseRepositoryDataJpa implements ClasseRepository {

	@Autowired
	private ClasseDataJpaRepo classeDataJpaRepo;

	public ClasseRepositoryDataJpa(ClasseDataJpaRepo classeDataJpaRepo) {
		this.classeDataJpaRepo = classeDataJpaRepo;
	}

	@Override
	public List<Classe> findAll() {
		return classeDataJpaRepo.findAll();
	}

	@Override
	public void save(Classe classe) {
		// TODO Auto-generated method stub
		classeDataJpaRepo.save(classe);
	}

	@Override
	public void update(Classe classeAvecId) {
		// TODO Auto-generated method stub
		classeDataJpaRepo.save(classeAvecId);
	}

	@Override
	public void delete(Classe classe) {
		// TODO Auto-generated method stub
		classeDataJpaRepo.delete(classe);
	}

	@Override
	public Optional<Classe> findById(Long id) {
		// TODO Auto-generated method stub
		Classe classe = classeDataJpaRepo.getOne(id);
		return Optional.ofNullable(classe);

	}

}
