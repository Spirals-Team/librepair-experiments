package br.edu.fapce.nexti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.fapce.nexti.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Usuario findByEmail(String email);
}
