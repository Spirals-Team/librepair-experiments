package ch.tbmelabs.tv.core.authorizationserver.domain;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope.ClientScopeAssociation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "clients")
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Client extends NicelyDocumentedJDBCResource {
  @Transient
  private static final long serialVersionUID = 1L;

  @Id
  @GenericGenerator(name = "pk_sequence", strategy = NicelyDocumentedJDBCResource.SEQUENCE_GENERATOR_STRATEGY, parameters = {
      @Parameter(name = "sequence_name", value = "clients_id_seq"), @Parameter(name = "increment_size", value = "1") })
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
  @Column(unique = true)
  private Long id;

  @NotEmpty
  @Length(max = 36, min = 36)
  @Column(name = "client_id", columnDefinition = "bpchar(36)", unique = true)
  private String clientId;

  @Length(max = 36, min = 36)
  @Column(columnDefinition = "bpchar(36)")
  @JsonProperty(access = Access.WRITE_ONLY)
  private String secret;

  @NotNull
  @Column(name = "secret_required")
  private Boolean isSecretRequired = true;

  @NotNull
  @Column(name = "auto_approve")
  private Boolean isAutoApprove = false;

  @NotNull
  @Column(name = "access_token_validity")
  private Integer accessTokenValiditySeconds;

  @NotNull
  @Column(name = "refresh_token_validity")
  private Integer refreshTokenValiditySeconds;

  @Length(max = 256)
  private String redirectUri;

  @JsonManagedReference("client_has_grant_types")
  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(cascade = { CascadeType.MERGE }, mappedBy = "client")
  private Collection<ClientGrantTypeAssociation> grantTypes;

  @JsonManagedReference("client_has_authorities")
  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(cascade = { CascadeType.MERGE }, mappedBy = "client")
  private Collection<ClientAuthorityAssociation> grantedAuthorities;

  @JsonManagedReference("client_has_scopes")
  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(cascade = { CascadeType.MERGE }, mappedBy = "client")
  private Collection<ClientScopeAssociation> scopes;

  public Collection<ClientGrantTypeAssociation> grantTypesToAssociations(List<GrantType> grantTypeList) {
    return grantTypeList.stream().map(grantType -> new ClientGrantTypeAssociation(this, grantType))
        .collect(Collectors.toList());
  }

  public Collection<ClientAuthorityAssociation> authoritiesToAssociations(List<Authority> authorityList) {
    return authorityList.stream().map(authority -> new ClientAuthorityAssociation(this, authority))
        .collect(Collectors.toList());
  }

  public Collection<ClientScopeAssociation> scopesToAssociations(List<Scope> scopeList) {
    return scopeList.stream().map(scope -> new ClientScopeAssociation(this, scope)).collect(Collectors.toList());
  }
}