package ch.tbmelabs.tv.core.authorizationserver.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends NicelyDocumentedJDBCResource {
  @Transient
  private static final long serialVersionUID = 1L;

  @Transient
  @JsonIgnore
  public static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

  @Id
  @GenericGenerator(name = "pk_sequence", strategy = NicelyDocumentedJDBCResource.SEQUENCE_GENERATOR_STRATEGY, parameters = {
      @Parameter(name = "sequence_name", value = "users_id_seq"), @Parameter(name = "increment_size", value = "1") })
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
  @Column(unique = true)
  private Long id;

  @NotEmpty
  @Length(min = 5, max = 64)
  @Column(unique = true)
  private String username;

  @NotEmpty
  @Email
  @Length(max = 128)
  private String email;

  @NotEmpty
  @Length(min = 60, max = 60)
  @Column(columnDefinition = "bpchar(60)")
  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;

  @Transient
  @JsonProperty(access = Access.WRITE_ONLY)
  private String confirmation;

  @NotNull
  private Boolean isEnabled = false;

  @NotNull
  private Boolean isBlocked = false;

  @JsonBackReference
  @OneToOne(cascade = { CascadeType.MERGE }, mappedBy = "user")
  private EmailConfirmationToken emailConfirmationToken;

  @JsonManagedReference("user_has_roles")
  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(cascade = { CascadeType.MERGE }, mappedBy = "user")
  private Collection<UserRoleAssociation> roles;

  @Override
  @PrePersist
  public void onCreate() {
    super.onCreate();

    this.setPassword(PASSWORD_ENCODER.encode(this.getPassword()));
  }

  public UserRoleAssociation roleToAssociation(Role role) {
    return new UserRoleAssociation(this, role);
  }

  public Collection<UserRoleAssociation> rolesToAssociations(Collection<Role> roles) {
    return roles.stream().map(this::roleToAssociation).collect(Collectors.toList());
  }
}