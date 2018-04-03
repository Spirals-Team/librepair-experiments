package uo.asw.inciManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uo.asw.dbManagement.model.Usuario;
import uo.asw.inciManager.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	
	public void addUsuario(Usuario usuario) {
		usuarioRepository.save(usuario);
	}

}
