package Repository;

import org.springframework.data.repository.CrudRepository;

import br.edu.fapce.nexti.api.entities.Usuario;

public interface UsuarioRepository extends  CrudRepository<Usuario, Long> {

}
