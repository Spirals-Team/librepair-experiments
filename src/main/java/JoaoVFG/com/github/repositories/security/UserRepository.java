package JoaoVFG.com.github.repositories.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import JoaoVFG.com.github.entity.security.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Transactional(readOnly = true)
	@Query("SELECT user FROM User user WHERE user.id = :idBusca")
	public User buscaPorId(@Param("idBusca")Integer id);
	
	public User findByemail(String email);
}
