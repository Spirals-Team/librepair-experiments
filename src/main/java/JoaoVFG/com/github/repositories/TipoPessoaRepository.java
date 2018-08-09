package JoaoVFG.com.github.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import JoaoVFG.com.github.entity.TipoPessoa;

@Repository
public interface TipoPessoaRepository extends JpaRepository<TipoPessoa, Integer> {

	@Transactional(readOnly = true)
	@Query("SELECT tipoPessoa FROM TipoPessoa tipoPessoa WHERE tipoPessoa.id = :id")
	public TipoPessoa buscaPorId(@Param("id") Integer id);

	TipoPessoa findBydescricao(String descricao);

	TipoPessoa findByid(Integer id);
}
