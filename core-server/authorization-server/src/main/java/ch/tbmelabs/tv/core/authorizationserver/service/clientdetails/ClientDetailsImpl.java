package ch.tbmelabs.tv.core.authorizationserver.service.clientdetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociation;

public class ClientDetailsImpl implements ClientDetails {
  private static final long serialVersionUID = 1L;

  public static final String CLIENT_REDIRECT_URI_SPLITTERATOR = ",";

  private Client client;

  public ClientDetailsImpl(Client client) {
    this.client = client;
  }

  @Override
  public String getClientId() {
    return client.getClientId();
  }

  @Override
  public Set<String> getResourceIds() {
    // TODO: What is this?
    return new HashSet<>();
  }

  @Override
  public boolean isSecretRequired() {
    return client.getIsSecretRequired().booleanValue();
  }

  @Override
  public boolean isAutoApprove(String scope) {
    return client.getIsAutoApprove().booleanValue();
  }

  @Override
  public String getClientSecret() {
    return client.getSecret();
  }

  @Override
  public Integer getAccessTokenValiditySeconds() {
    return client.getAccessTokenValiditySeconds();
  }

  @Override
  public Integer getRefreshTokenValiditySeconds() {
    return client.getRefreshTokenValiditySeconds();
  }

  @Override
  public boolean isScoped() {
    return !getScope().isEmpty();
  }

  @Override
  public Set<String> getAuthorizedGrantTypes() {
    return client.getGrantTypes().stream().map(association -> association.getClientGrantType().getName())
        .collect(Collectors.toSet());
  }

  @Override
  public Set<String> getScope() {
    return client.getScopes().stream().map(association -> association.getClientScope().getName())
        .collect(Collectors.toSet());
  }

  @Override
  public Set<String> getRegisteredRedirectUri() {
    return new HashSet<>(Arrays.asList(client.getRedirectUri().split(CLIENT_REDIRECT_URI_SPLITTERATOR)));
  }

  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    return client.getGrantedAuthorities().stream().map(ClientAuthorityAssociation::getClientAuthority)
        .collect(Collectors.toList());
  }

  @Override
  public Map<String, Object> getAdditionalInformation() {
    // TODO: Is additional information required?
    return new HashMap<>();
  }
}