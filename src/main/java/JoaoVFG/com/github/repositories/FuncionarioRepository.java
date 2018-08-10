package JoaoVFG.com.github.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import JoaoVFG.com.github.entity.Cargo;
import JoaoVFG.com.github.entity.Empresa;
import JoaoVFG.com.github.entity.Funcionario;
import JoaoVFG.com.github.entity.Pessoa;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer>{
	
	@Transactional(readOnly = true)
	@Query("SELECT funcionario FROM Funcionario funcionario WHERE funcionario.id = :id")
	public Funcionario buscaPorId(@Param("id") Integer id);
	
	@Transactional(readOnly = true)
	public Funcionario findBypessoa(Pessoa pessoa);
	
	@Transactional(readOnly = true)
	public List<Funcionario> findByempresa(Empresa empresa);
	
	@Transactional(readOnly = true)
	@Query("SELECT funcionario FROM Funcionario funcionario WHERE funcionario.empresa = :empresa AND funcionario.cargo = :cargo")
	public List<Funcionario> buscaPorEmpresaECargo(@Param("empresa")Empresa empresa,@Param("cargo") Cargo cargo);
}
