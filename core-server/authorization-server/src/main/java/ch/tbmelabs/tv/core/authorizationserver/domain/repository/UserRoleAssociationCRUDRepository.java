package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;

@Repository
public interface UserRoleAssociationCRUDRepository extends CrudRepository<UserRoleAssociation, Long> {
  Collection<UserRoleAssociation> findAllByUser(User user);
}