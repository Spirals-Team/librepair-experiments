package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociation;

@Repository
public interface ClientGrantTypeAssociationCRUDRepository extends CrudRepository<ClientGrantTypeAssociation, Long> {
}