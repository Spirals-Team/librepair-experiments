package ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope.ClientScopeAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.ClientDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientAuthorityAssociationCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientGrantTypeAssociationCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientScopeAssociationCRUDRepository;

@Component
public class ClientDTOMapper {
  @Autowired
  private ClientGrantTypeAssociationCRUDRepository clientGrantTypeRepository;

  @Autowired
  private ClientAuthorityAssociationCRUDRepository clientAuthorityRepository;

  @Autowired
  private ClientScopeAssociationCRUDRepository clientScopeRepository;

  public ClientDTO toClientDTO(Client client) {
    return new ClientDTO(client,
        clientGrantTypeRepository.findAllByClient(client).stream().map(ClientGrantTypeAssociation::getClientGrantType)
            .collect(Collectors.toList()),
        clientAuthorityRepository.findAllByClient(client).stream().map(ClientAuthorityAssociation::getClientAuthority)
            .collect(Collectors.toList()),
        clientScopeRepository.findAllByClient(client).stream().map(ClientScopeAssociation::getClientScope)
            .collect(Collectors.toList()));
  }

  public Client toClient(ClientDTO clientDTO) {
    Client client = new Client();

    client.setCreated(clientDTO.getCreated());
    client.setLastUpdated(clientDTO.getLastUpdated());
    client.setId(clientDTO.getId());
    client.setClientId(clientDTO.getClientId());
    client.setSecret(clientDTO.getSecret());
    client.setIsSecretRequired(clientDTO.getIsSecretRequired());
    client.setIsAutoApprove(clientDTO.getIsAutoApprove());
    client.setAccessTokenValiditySeconds(clientDTO.getAccessTokenValiditySeconds());
    client.setRefreshTokenValiditySeconds(clientDTO.getRefreshTokenValiditySeconds());

    List<ClientGrantTypeAssociation> clientGrantTypeAssociations = (List<ClientGrantTypeAssociation>) clientGrantTypeRepository
        .findAllByClient(client);
    clientDTO.getGrantTypes().stream()
        .filter(userGrantType -> clientGrantTypeAssociations.stream().noneMatch(
            existingGrantType -> existingGrantType.getClientGrantType().getName().equals(userGrantType.getName())))
        .map(client::grantTypeToAssociation).collect(Collectors.toList());
    client.setGrantTypes(clientGrantTypeAssociations);

    List<ClientAuthorityAssociation> clientAuthorityAssociations = (List<ClientAuthorityAssociation>) clientAuthorityRepository
        .findAllByClient(client);
    clientDTO.getGrantedAuthorities().stream()
        .filter(clientAuthority -> clientAuthorityAssociations.stream().noneMatch(
            existingAuthority -> existingAuthority.getClientAuthority().getName().equals(clientAuthority.getName())))
        .map(client::authorityToAssociation).collect(Collectors.toList());
    client.setGrantedAuthorities(clientAuthorityAssociations);

    List<ClientScopeAssociation> clientScopeAssociations = (List<ClientScopeAssociation>) clientScopeRepository
        .findAllByClient(client);
    clientDTO.getScopes().stream()
        .filter(clientScope -> clientScopeAssociations.stream()
            .noneMatch(existingScope -> existingScope.getClientScope().getName().equals(clientScope.getName())))
        .map(client::scopeToAssociation).collect(Collectors.toList());
    client.setScopes(clientScopeAssociations);

    return client;
  }
}