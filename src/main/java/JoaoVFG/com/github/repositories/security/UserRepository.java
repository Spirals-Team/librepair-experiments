package JoaoVFG.com.github.repositories.security;

import java.util.List;

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
	
	@Transactional(readOnly = true)
	public User findByemail(String email);
	
	@Transactional(readOnly = true)
	@Query("SELECT user FROM User user,Funcionario funcionario WHERE funcionario.empresa.id = :idEmpresa and funcionario.pessoa.id = user.pessoa.id")
	public List<User> findUsersByEmpresa(@Param("idEmpresa")Integer idEmpresa);
}
