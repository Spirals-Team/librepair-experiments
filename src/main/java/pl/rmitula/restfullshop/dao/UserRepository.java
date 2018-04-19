package pl.rmitula.restfullshop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.rmitula.restfullshop.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();

    User findByEmailIgnoreCase(String email);

    User findById(long id);

    User findByUsernameIgnoreCase(String username);
}
