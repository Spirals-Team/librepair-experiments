package JoaoVFG.com.github.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import JoaoVFG.com.github.entity.Empresa;
import JoaoVFG.com.github.entity.Regiao;

@Repository
public interface RegiaoRepository extends JpaRepository<Regiao, Integer>{
	
	@Transactional(readOnly = true)
	@Query("SELECT regiao FROM Regiao regiao WHERE regiao.id = :id")
	public Regiao buscaPorId(@Param("id") Integer id);
	
	@Transactional(readOnly = true)
	public Regiao findByempresa(Empresa empresa);
	
	@Transactional(readOnly = true)
	@Query("SELECT regiao FROM Regiao regiao WHERE regiao.empresa = :empresa and regiao.descricao = :descricao")
	public List<Regiao> findByEmpresaAndDescricao(@Param("empresa")Empresa empresa,@Param("descricao") String descricao);
	
	@Transactional(readOnly = true)
	@Query("SELECT regiao FROM Regiao regiao WHERE regiao.empresa.empresaMatrizId = :empresaMatrizId")
	public List<Regiao> findByEmpresaMatriz(@Param("empresaMatrizId")Integer emprezaMatrizId);
}
