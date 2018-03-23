package ch.tbmelabs.tv.core.authorizationserver.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@Table(name = "authentication_log")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationLog extends NicelyDocumentedJDBCResource {
  @Transient
  private static final long serialVersionUID = 1L;

  public enum AUTHENTICATION_STATE {
    OK, NOK
  }

  @Id
  @GenericGenerator(name = "pk_sequence", strategy = NicelyDocumentedJDBCResource.SEQUENCE_GENERATOR_STRATEGY, parameters = {
      @Parameter(name = "sequence_name", value = "authentication_log_id_seq"),
      @Parameter(name = "increment_size", value = "1") })
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
  @Column(unique = true)
  private Long id;

  @NotEmpty
  @Length(max = 3)
  private String state;

  @NotEmpty
  @Length(max = 45)
  @Column(columnDefinition = "bpchar(45)")
  private String ip;

  @Length(max = 256)
  private String message;

  @ManyToOne(cascade = CascadeType.MERGE)
  @LazyCollection(LazyCollectionOption.FALSE)
  @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  public AuthenticationLog(AUTHENTICATION_STATE authenticationState, String ip, String message, User user) {
    this.state = authenticationState.name();
    this.ip = ip;
    this.message = message;
    this.user = user;
  }

  public void setState(AUTHENTICATION_STATE authenticationState) {
    this.state = authenticationState.name();
  }
}