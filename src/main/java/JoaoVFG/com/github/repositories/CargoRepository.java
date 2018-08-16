package JoaoVFG.com.github.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import JoaoVFG.com.github.entity.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer>{
	
	@Transactional(readOnly = true)
	@Query("SELECT cargo FROM Cargo cargo WHERE cargo.id = :id")
	public Cargo buscaPorId(@Param("id") Integer id);
	
	@Transactional(readOnly = true)
	public List<Cargo> findBydescricaoContains(String descricao);
	
	@Transactional(readOnly = true)
	public List<Cargo> findBydescricao(String descricao);
	
}
