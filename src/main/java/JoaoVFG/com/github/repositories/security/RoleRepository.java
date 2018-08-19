package JoaoVFG.com.github.repositories.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import JoaoVFG.com.github.entity.security.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	@Transactional(readOnly = true)
	@Query("SELECT role FROM Role role WHERE role.id = :idBusca")
	public Role buscaPorId(@Param("idBusca")Integer id);
}
