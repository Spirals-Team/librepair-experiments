package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;

@Repository
public interface ClientCRUDRepository extends CrudRepository<Client, Long> {
  Client findByClientId(String clientId);
}