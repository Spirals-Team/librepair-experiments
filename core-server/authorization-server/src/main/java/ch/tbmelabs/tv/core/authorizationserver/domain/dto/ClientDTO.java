package ch.tbmelabs.tv.core.authorizationserver.domain.dto;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;
import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.GrantType;
import ch.tbmelabs.tv.core.authorizationserver.domain.Scope;
import lombok.Data;

@Data
public class ClientDTO {
  private Date created;

  private Date lastUpdated;

  private Long id;

  private String clientId;

  private String secret;

  private Boolean isSecretRequired;

  private Boolean isAutoApprove;

  private Integer accessTokenValiditySeconds;

  private Integer refreshTokenValiditySeconds;

  private String redirectUri;

  @JsonIgnoreProperties({ "clientsWithGrantTypes" })
  private Collection<GrantType> grantTypes;

  @JsonIgnoreProperties({ "clientsWithAuthorities" })
  private Collection<Authority> grantedAuthorities;

  @JsonIgnoreProperties({ "clientsWithScopes" })
  private Collection<Scope> scopes;

  public ClientDTO(Client client, List<GrantType> grantTypes, List<Authority> authorities, List<Scope> scopes) {
    this.created = client.getCreated();
    this.lastUpdated = client.getLastUpdated();
    this.id = client.getId();
    this.clientId = client.getClientId();
    this.secret = client.getSecret();
    this.isSecretRequired = client.getIsSecretRequired();
    this.isAutoApprove = client.getIsAutoApprove();
    this.accessTokenValiditySeconds = client.getAccessTokenValiditySeconds();
    this.refreshTokenValiditySeconds = client.getRefreshTokenValiditySeconds();
    this.redirectUri = client.getRedirectUri();
    this.grantTypes = grantTypes;
    this.grantedAuthorities = authorities;
    this.scopes = scopes;
  }
}