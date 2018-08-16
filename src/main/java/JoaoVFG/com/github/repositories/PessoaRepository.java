package JoaoVFG.com.github.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import JoaoVFG.com.github.entity.Pessoa;
import JoaoVFG.com.github.entity.TipoPessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

	@Transactional(readOnly = true)
	@Query("SELECT pessoa FROM Pessoa pessoa WHERE pessoa.id = :id")
	public Pessoa buscaPorId(@Param("id") Integer id);

	@Transactional(readOnly = true)
	public List<Pessoa> findBytipo(TipoPessoa tipoPessoa);

	@Transactional(readOnly = true)
	public Pessoa findBycpf(String cpf);

	@Transactional(readOnly = true)
	public Pessoa findBycnpj(String cnpj);

	@Transactional(readOnly = true)
	public List<Pessoa> findByrazaoSocialContains(String razaoSocial);

}
