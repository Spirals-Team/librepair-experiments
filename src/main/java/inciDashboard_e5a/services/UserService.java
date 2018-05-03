package inciDashboard_e5a.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import inciDashboard_e5a.model.User;
import inciDashboard_e5a.repositories.UsersRepository;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void addUser(User user) {
	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	usersRepository.save(user);
    }

}
