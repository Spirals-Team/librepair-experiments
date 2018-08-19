package JoaoVFG.com.github.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import JoaoVFG.com.github.entity.Cep;
import JoaoVFG.com.github.entity.Cidade;
import JoaoVFG.com.github.entity.Estado;

public interface CepRepository extends JpaRepository<Cep, Integer>{
	
	@Transactional(readOnly = true)
	@Query("SELECT cep FROM Cep cep WHERE cep.id = :idBusca")
	public Cep buscaPorId(@Param("idBusca")Integer id);
	
	
	//busca Cep por Cep
	@Transactional(readOnly = true)
	@Query("SELECT cep FROM Cep cep WHERE cep.cep = :cepBusca")
	public Cep findBycep(@Param("cepBusca") String cepBusca);
	
	//busca Cep por nome Rua
	@Transactional(readOnly = true)
	public List<Cep> findBynomeRua(String nomeRua);
	
	
	@Transactional(readOnly = true)
	@Query("SELECT cep FROM Cep cep WHERE cep.cidade = :cidadeBusca and cep.cidade.estado = :estadoBusca")
	public List<Cep> findByCidade(@Param("cidadeBusca") Cidade cidade, @Param("estadoBusca") Estado estado);
	
	@Transactional(readOnly = true)
	@Query("SELECT cep FROM Cep cep WHERE cep.cidade = :cidadeBusca and cep.bairro = :nomeBairro")
	public List<Cep> findByBairroAndCidade(@Param("cidadeBusca") Cidade cidade, @Param("nomeBairro") String nomeBairro);

}
