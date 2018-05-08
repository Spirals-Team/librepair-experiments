package inciManager.uo.asw.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inciManager.uo.asw.dbManagement.model.Usuario;
import inciManager.uo.asw.mvc.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void addUsuario(Usuario usuario) {
		usuarioRepository.save(usuario);
	}

}
