package JoaoVFG.com.github.service.test;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import JoaoVFG.com.github.entity.Pessoa;
import JoaoVFG.com.github.entity.security.Role;
import JoaoVFG.com.github.entity.security.User;
import JoaoVFG.com.github.repositories.PessoaRepository;
import JoaoVFG.com.github.repositories.security.RoleRepository;
import JoaoVFG.com.github.repositories.security.UserRepository;

@Service
public class DBServiceTestUsers {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	PessoaRepository pessoaRepository;

	public void instantiabeDBUsers() {

		Pessoa pessoaf1 = pessoaRepository.buscaPorId(1);
		Pessoa pessoaf2 = pessoaRepository.buscaPorId(2);
		Pessoa pessoaf3 = pessoaRepository.buscaPorId(3);

		Role role = roleRepository.buscaPorId(1);
		Role role2 = roleRepository.buscaPorId(2);
		Role role3 = roleRepository.buscaPorId(3);
		Role role4 = roleRepository.buscaPorId(4);
		Role role5 = roleRepository.buscaPorId(5);
		Role role6 = roleRepository.buscaPorId(6);
		Role role7 = roleRepository.buscaPorId(7);
		Role role8 = roleRepository.buscaPorId(8);
		HashSet<Role> roles1 = new HashSet<>(Arrays.asList(role));
		HashSet<Role> roles2 = new HashSet<>(Arrays.asList(role2, role3, role4));
		HashSet<Role> roles3 = new HashSet<>(Arrays.asList(role5, role6, role7, role8));

		User user1 = new User(null, "adm@adm.com.br", passwordEncoder.encode("adm"), pessoaf1, roles1);
		User user2 = new User(null, "pes2@domain.com.br", passwordEncoder.encode("123"), pessoaf2, roles2);
		User user3 = new User(null, "pes3@domain.com.br", passwordEncoder.encode("asd"), pessoaf3, roles3);
		userRepository.saveAll(Arrays.asList(user1, user2, user3));

	}

}
