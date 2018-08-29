package br.edu.fapce.nexti.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.fapce.nexti.security.JwtUserFactory;
import br.edu.fapce.nexti.security.model.LoginUser;
import br.edu.fapce.nexti.security.model.LoginUserService;

@Service
public class MyUserDetailService implements UserDetailsService{

	@Autowired
	private LoginUserService loginUserService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginUser funcionario = loginUserService.findByEmail(username);
		if (funcionario != null) {
			return JwtUserFactory.create(funcionario);
		}

		throw new UsernameNotFoundException("Email não encontrado.");
	}
}
