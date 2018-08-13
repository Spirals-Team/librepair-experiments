package JoaoVFG.com.github.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import JoaoVFG.com.github.entity.Empresa;
import JoaoVFG.com.github.entity.Regiao;

@Repository
public interface RegiaoRepository extends JpaRepository<Regiao, Integer>{
	
	@Transactional(readOnly = true)
	@Query("SELECT regiao FROM Regiao regiao WHERE regiao.id = :id")
	public Regiao buscaPorId(@PathVariable("id") String id);
	
	@Transactional(readOnly = true)
	public List<Regiao> findByempresa(Empresa empresa);
	
	@Transactional(readOnly = true)
	@Query("SELECT regiao FROM Regiao regiao WHERE regiao.empresa = :empresa and regiao.descricao = :descricao")
	public List<Regiao> findByEmpresaAndDescricao(@PathVariable("empresa")Empresa empresa,@PathVariable("descricao") String descricao);
	
}
