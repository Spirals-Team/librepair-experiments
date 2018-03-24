package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;

@Repository
public interface ClientCRUDRepository extends CrudRepository<Client, Long> {
  Page<Client> findAll(Pageable pageable);

  Client findByClientId(String clientId);
}