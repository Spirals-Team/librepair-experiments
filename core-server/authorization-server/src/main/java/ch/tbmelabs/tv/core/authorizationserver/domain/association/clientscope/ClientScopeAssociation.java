package ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.NicelyDocumentedJDBCResource;
import ch.tbmelabs.tv.core.authorizationserver.domain.Scope;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@Table(name = "client_has_scopes")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@IdClass(ClientScopeAssociationId.class)
public class ClientScopeAssociation extends NicelyDocumentedJDBCResource {
  @Transient
  private static final long serialVersionUID = 1L;

  @Id
  @NotNull
  @Column(name = "client_id")
  private Long clientId;

  @Id
  @NotNull
  @Column(name = "client_scope_id")
  private Long clientScopeId;

  @JsonBackReference("client_has_scopes")
  @LazyCollection(LazyCollectionOption.FALSE)
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(insertable = false, updatable = false)
  @PrimaryKeyJoinColumn(name = "client_id", referencedColumnName = "id")
  private Client client;

  @JsonBackReference("scope_has_clients")
  @LazyCollection(LazyCollectionOption.FALSE)
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(insertable = false, updatable = false)
  @PrimaryKeyJoinColumn(name = "client_scope_id", referencedColumnName = "id")
  private Scope clientScope;

  public ClientScopeAssociation(Client client, Scope scope) {
    this.client = client;
    this.clientId = client.getId();
    this.clientScope = scope;
    this.clientScopeId = scope.getId();
  }

  public ClientScopeAssociation setClient(Client client) {
    this.client = client;
    this.clientId = client.getId();

    return this;
  }

  public ClientScopeAssociation setClientScope(Scope scope) {
    this.clientScope = scope;
    this.clientScopeId = scope.getId();

    return this;
  }

  public Client getClient() {
    return this.client;
  }

  public Scope getClientScope() {
    return this.clientScope;
  }

  @Override
  public String toString() {
    try {
      return new ObjectMapper().writeValueAsString(this);
    } catch (JsonProcessingException e) {
      return super.toString();
    }
  }
}