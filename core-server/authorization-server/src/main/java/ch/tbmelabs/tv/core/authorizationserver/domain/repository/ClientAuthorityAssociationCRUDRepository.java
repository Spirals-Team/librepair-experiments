package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociation;

@Repository
public interface ClientAuthorityAssociationCRUDRepository extends CrudRepository<ClientAuthorityAssociation, Long> {
  Collection<ClientAuthorityAssociation> findAllByClient(Client client);
}