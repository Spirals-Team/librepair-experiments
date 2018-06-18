package br.com.modelo.pontointeligente.api.security.services.impl;


import br.com.modelo.pontointeligente.api.security.entities.Usuario;
import br.com.modelo.pontointeligente.api.security.repositories.UsuarioRepository;
import br.com.modelo.pontointeligente.api.security.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Optional<Usuario> buscarPorEmail(String email) {
		return Optional.ofNullable(this.usuarioRepository.findByEmail(email));
	}
}