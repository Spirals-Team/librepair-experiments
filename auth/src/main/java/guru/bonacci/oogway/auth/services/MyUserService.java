package guru.bonacci.oogway.auth.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import guru.bonacci.oogway.auth.models.User;
import guru.bonacci.oogway.auth.repositories.UserRepository;

@Service
public class MyUserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findByUsername(s);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new UsernameNotFoundException(String.format("Username[%s] not found", s));
		}
	}

	public User loadUserByApiKey(String s) {
		Optional<User> user = userRepo.findByApiKey(s);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new UsernameNotFoundException(String.format("ApiKey[%s] not found", s));
		}
	}

	public User registerUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}
}
