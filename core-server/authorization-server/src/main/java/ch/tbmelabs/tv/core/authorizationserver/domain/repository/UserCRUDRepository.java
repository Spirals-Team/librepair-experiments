package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;

@Repository
public interface UserCRUDRepository extends CrudRepository<User, Long> {
  User findByUsername(String username);

  User findByEmail(String email);
}