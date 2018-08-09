package JoaoVFG.com.github.security;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import JoaoVFG.com.github.entity.security.User;
import JoaoVFG.com.github.repositories.security.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = Optional.ofNullable(userRepository.findByemail(username)).orElseThrow(
				() -> new UsernameNotFoundException("Usuário não encontrado com email :  " + username));
		return UserPrincipal.create(user);
	}

	@Transactional
	public UserDetails loadByUserId(Integer id) {
		User user = userRepository.findById(id).orElseThrow(
	            () -> new UsernameNotFoundException("Usuário não encontrado com id : " + id));
		return UserPrincipal.create(user);
	}

}
