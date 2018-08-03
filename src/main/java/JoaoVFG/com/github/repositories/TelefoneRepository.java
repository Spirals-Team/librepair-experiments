package JoaoVFG.com.github.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import JoaoVFG.com.github.entity.Pessoa;
import JoaoVFG.com.github.entity.Telefone;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Integer> {

	@Transactional(readOnly = true)
	@Query("SELECT telefone FROM Telefone telefone WHERE telefone.id = :id")
	public Telefone buscaPorId(@Param("id") Integer id);

	@Transactional(readOnly = true)
	public List<Telefone> findBypessoa(Pessoa pessoa);

	@Transactional(readOnly = true)
	@Query("SELECT telefone FROM Telefone telefone WHERE telefone.tipoNumero = :tipo AND telefone.pessoa.id = :pessoaId")
	public List<Telefone> findBypessoaAndtipoNumero(@Param("pessoaId") Integer pessoaId,
			@Param("tipo") String tipoNumero);

}
