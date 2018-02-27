package poe.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poe.spring.annotation.Chrono;
import poe.spring.domain.User;
import poe.spring.exception.DuplicateLoginBusinessException;
import poe.spring.repository.UserRepository;

@Service
public class UserManagerService {

	@Autowired
	private UserRepository userRepository;

	@Chrono
	public User signup(String login, String password) throws DuplicateLoginBusinessException{
        User user = null;
		if (userRepository.findByLogin(login) == null) {
			user = new User();
			user.setLogin(login);
			user.setPassword(password);
			userRepository.save(user);
		} else {
            throw new DuplicateLoginBusinessException();
		}
		return user;
	}

	public List<User> listUsers() {
		return (List<User>) userRepository.findAll();
	}

	public User getUserById(Long id) {
		return userRepository.findOne(id);
	}

	public User getUserByLogin(String login) {
		return userRepository.findByLogin(login);
	}

	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	public User updateUser(User user, String login, String password) {
		user.setLogin(login);
		user.setPassword(password);
		userRepository.save(user);
		return user;
	}
}