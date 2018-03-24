package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;

@Repository
public interface UserRoleAssociationRepository extends CrudRepository<UserRoleAssociation, Long> {
}