package br.edu.fapce.nexti.security.repository;

import org.springframework.data.repository.CrudRepository;

import br.edu.fapce.nexti.security.model.LoginUser;

public interface LoginUserRepository extends CrudRepository<LoginUser, Long>{
	
	LoginUser findByEmail(String email);
	
	void deleteById(Long Id);
	
	LoginUser findById(Long loginUserId);
	
}
