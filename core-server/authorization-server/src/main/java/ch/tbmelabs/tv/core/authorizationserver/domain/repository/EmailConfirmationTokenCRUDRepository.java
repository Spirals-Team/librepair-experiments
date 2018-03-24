package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.core.authorizationserver.domain.EmailConfirmationToken;

@Repository
public interface EmailConfirmationTokenCRUDRepository extends CrudRepository<EmailConfirmationToken, Long> {
  EmailConfirmationToken findByTokenString(String tokenString);
}