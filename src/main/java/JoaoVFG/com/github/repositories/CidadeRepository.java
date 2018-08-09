package JoaoVFG.com.github.repositories;

import java.util.LinkedList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import JoaoVFG.com.github.entity.Cidade;
import JoaoVFG.com.github.entity.Estado;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
	
	@Transactional(readOnly = true)
	@Query("SELECT cidade FROM Cidade cidade WHERE cidade.id = :idBusca")
	public Cidade buscaPorId(@Param("idBusca")Integer id);
	
	//Busca de cidades por nome
	@Transactional(readOnly = true)
	public LinkedList<Cidade> findByNomeContains(String nome);
	
	@Transactional(readOnly = true)
	public Cidade findBynome(String nome);
	
	//Busca de Cidades por id_estado
	@Transactional(readOnly = true)
	public LinkedList<Cidade> findByEstado(Estado estado);
	
	//Busca de Cidades por nome e Id_estado
	@Transactional(readOnly = true)
	@Query("SELECT cidade FROM Cidade cidade WHERE cidade.estado.id = :estadoId AND cidade.nome like :cidadeNome")
	public LinkedList<Cidade> findCidadesEstadoIdNomeCidades(@Param("estadoId")Integer estadoId, @Param("cidadeNome")String cidadeNome);
	
	
	//Busca Auxiliar cidades por nome e sigla do estado
	@Transactional(readOnly = true)
	@Nullable
	@Query("SELECT cidade FROM Cidade cidade WHERE cidade.estado.id = :estadoId AND cidade.nome like :cidadeNome")
	public Cidade findCidadesEstadoIdNomeCidadesAux(@Nullable@Param("estadoId")Integer estadoId, @Nullable@Param("cidadeNome")String cidadeNome);
	
}
