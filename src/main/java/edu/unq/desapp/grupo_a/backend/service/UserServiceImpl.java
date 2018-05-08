package edu.unq.desapp.grupo_a.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.unq.desapp.grupo_a.backend.dao.UserDao;
import edu.unq.desapp.grupo_a.backend.dto.UserDto;
import edu.unq.desapp.grupo_a.backend.model.User;

@Transactional
@Service
public class UserServiceImpl extends GenericService<User> {

	@Autowired
	private UserDao repository;

	public UserServiceImpl() {
	}

	public UserServiceImpl(UserDao repo) {
		this.setRepository(repo);
	}

	public UserDao getRepository() {
		return repository;
	}

	public void setRepository(UserDao repository) {
		this.repository = repository;
	}

	//ToDo... implement
	//public void saveuser(UserDto dto) {
		
	//}

}
