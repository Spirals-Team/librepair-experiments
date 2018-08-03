package JoaoVFG.com.github.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import JoaoVFG.com.github.entity.Empresa;
import JoaoVFG.com.github.entity.TipoEmpresa;

@Repository
public interface TipoEmpresaRepository extends JpaRepository<Empresa, Integer>{

	@Transactional(readOnly = true)
	@Query("SELECT tipoEmpresa FROM TipoEmpresa tipoEmpresa WHERE tipoEmpresa.id = :id")
	public TipoEmpresa buscaPorId(@Param("id") Integer id);
	
	@Transactional(readOnly = true)
	@Query("SELECT tipoEmpresa FROM TipoEmpresa tipoEmpresa WHERE tipoEmpresa.descricao = :descricao")
	public TipoEmpresa buscaPorDescricao(String descricao);
}
