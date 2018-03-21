package ch.tbmelabs.tv.core.authorizationserver.web.oauth2;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope.ClientScopeAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientScopeAssociationCRUDRepository;

@RestController
public class OAuth2ApprovalClientScopesController {
  @Autowired
  private ClientCRUDRepository clientRepository;

  @Autowired
  private ClientScopeAssociationCRUDRepository clientScopeAssociationRepository;

  @SuppressWarnings("unchecked")
  @RequestMapping("/oauth/confirm_access_scopes")
  public List<String> getAccessConfirmation(@RequestParam(name = "client_id", required = true) String clientId) {
    return (List<String>) IteratorUtils
        .toList(clientScopeAssociationRepository.findByClient(clientRepository.findByClientId(clientId)).iterator())
        .stream().map(association -> ((ClientScopeAssociation) association).getClientScope().getName())
        .collect(Collectors.toList());
  }
}