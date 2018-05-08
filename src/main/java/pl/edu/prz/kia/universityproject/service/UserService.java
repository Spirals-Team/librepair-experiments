package pl.edu.prz.kia.universityproject.service;

import pl.edu.prz.kia.universityproject.model.User;

public interface UserService {
	User findUserByEmail(String email);
	void saveUser(User user);
}
