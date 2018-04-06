package br.com.spako.api.repositorio;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.spako.api.entidades.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{
	
	Optional<Usuario> findById(Long id);
	
	Usuario findByEmail(String email);
	
	Usuario findByNome(String nome);
}

