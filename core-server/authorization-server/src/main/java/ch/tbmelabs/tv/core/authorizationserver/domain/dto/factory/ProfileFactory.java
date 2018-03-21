package ch.tbmelabs.tv.core.authorizationserver.domain.dto.factory;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserProfile;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserRoleAssociationCRUDRepository;

@Component
public class ProfileFactory {
  @Autowired
  private UserRoleAssociationCRUDRepository userRoleAssociationRepository;

  @SuppressWarnings("unchecked")
  public UserProfile getUserProfile(User user) {
    return new UserProfile(user,
        ((List<UserRoleAssociation>) IteratorUtils.toList(userRoleAssociationRepository.findAllByUser(user).iterator()))
            .stream().map(UserRoleAssociation::getUserRole).collect(Collectors.toList()));
  }
}