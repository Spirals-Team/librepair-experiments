package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope.ClientScopeAssociation;

@Repository
public interface ClientScopeAssociationCRUDRepository extends CrudRepository<ClientScopeAssociation, Long> {
  Collection<ClientScopeAssociation> findAllByClient(Client client);
}