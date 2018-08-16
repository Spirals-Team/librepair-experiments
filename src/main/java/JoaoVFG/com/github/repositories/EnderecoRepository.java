package JoaoVFG.com.github.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import JoaoVFG.com.github.entity.Cep;
import JoaoVFG.com.github.entity.Endereco;
import JoaoVFG.com.github.entity.Pessoa;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

	@Transactional(readOnly = true)
	@Query("SELECT endereco FROM Endereco endereco WHERE endereco.id = :id")
	public Endereco buscaPorId(@Param("id") Integer id);

	@Transactional(readOnly = true)
	List<Endereco> findBypessoa(Pessoa pessoa);

	@Transactional(readOnly = true)
	List<Endereco> findBycep(Cep cep);

}
