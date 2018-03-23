package ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole;

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

import ch.tbmelabs.tv.core.authorizationserver.domain.NicelyDocumentedJDBCResource;
import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@Table(name = "user_has_roles")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@IdClass(UserRoleAssociationId.class)
public class UserRoleAssociation extends NicelyDocumentedJDBCResource {
  @Transient
  private static final long serialVersionUID = 1L;

  @Id
  @NotNull
  @Column(name = "user_id")
  private Long userId;

  @Id
  @NotNull
  @Column(name = "user_role_id")
  private Long userRoleId;

  @JsonBackReference("user_has_roles")
  @LazyCollection(LazyCollectionOption.FALSE)
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(insertable = false, updatable = false)
  @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @JsonBackReference("role_has_users")
  @LazyCollection(LazyCollectionOption.FALSE)
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(insertable = false, updatable = false)
  @PrimaryKeyJoinColumn(name = "user_role_id", referencedColumnName = "id")
  private Role userRole;

  public UserRoleAssociation(User user, Role role) {
    this.user = user;
    this.userId = user.getId();
    this.userRole = role;
    this.userRoleId = role.getId();
  }

  public UserRoleAssociation setUser(User user) {
    this.user = user;
    this.userId = user.getId();

    return this;
  }

  public UserRoleAssociation setUserRole(Role role) {
    this.userRole = role;
    this.userRoleId = role.getId();

    return this;
  }

  public User getUser() {
    return this.user;
  }

  public Role getUserRole() {
    return this.userRole;
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