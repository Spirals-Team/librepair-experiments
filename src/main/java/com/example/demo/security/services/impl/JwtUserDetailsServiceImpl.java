package com.example.demo.security.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.security.JwtUserFactory;
//import com.example.demo.security.entities.Usuario;
//import com.example.demo.security.services.UsuarioService;

import com.example.demo.entities.Funcionario;
import com.example.demo.services.FuncionarioService;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

//	@Autowired
//	private UsuarioService usuarioService;
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Optional<Usuario> funcionario = usuarioService.buscarPorEmail(username);
//
//		if (funcionario.isPresent()) {
//			return JwtUserFactory.create(funcionario.get());
//		}
//
//		throw new UsernameNotFoundException("Email não encontrado.");
//	}

	@Autowired
	private FuncionarioService funcionarioService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Funcionario> funcionario = funcionarioService.buscarPorEmail(username);

		if (funcionario.isPresent()) {
			return JwtUserFactory.create(funcionario.get());
		}

		throw new UsernameNotFoundException("Email não encontrado.");
	}

}

