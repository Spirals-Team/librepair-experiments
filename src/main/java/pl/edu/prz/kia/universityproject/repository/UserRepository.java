package pl.edu.prz.kia.universityproject.repository;

import org.springframework.data.jpa.repository.Query;
import pl.edu.prz.kia.universityproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	 User findByEmail(String email);
	 User getOne(Long id);

	@Query("SELECT u FROM User u JOIN u.roles ur WHERE (SELECT COUNT(uo) FROM u.roles uo JOIN u.roles ur) = 1")
	List<User> findAllUsersCustomQuery();
}
