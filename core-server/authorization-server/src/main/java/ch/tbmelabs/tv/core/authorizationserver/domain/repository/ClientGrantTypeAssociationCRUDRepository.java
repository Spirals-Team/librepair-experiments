package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociation;

@Repository
public interface ClientGrantTypeAssociationCRUDRepository extends CrudRepository<ClientGrantTypeAssociation, Long> {
  Collection<ClientGrantTypeAssociation> findAllByClient(Client client);
}