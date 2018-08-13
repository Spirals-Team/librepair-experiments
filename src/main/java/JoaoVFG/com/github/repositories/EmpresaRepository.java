package JoaoVFG.com.github.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import JoaoVFG.com.github.entity.Empresa;
import JoaoVFG.com.github.entity.Pessoa;
import JoaoVFG.com.github.entity.TipoEmpresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer>{
	
	
	@Transactional(readOnly = true)
	@Query("SELECT empresa FROM Empresa empresa WHERE empresa.id = :idBusca")
	public Empresa buscaPorId(@Param("idBusca")Integer id);
	
	@Transactional(readOnly = true)
	public Empresa findBypessoa(Pessoa pessoa);
	
	@Transactional(readOnly = true)
	public List<Empresa> findBytipoEmpresa(TipoEmpresa tipoEmpresa);
	
	@Transactional(readOnly = true)
	public List<Empresa> findByTransportadora(Integer transportadora);
	
	@Transactional(readOnly = true)
	public List<Empresa> findByempresaMatrizId(Integer id);
}
