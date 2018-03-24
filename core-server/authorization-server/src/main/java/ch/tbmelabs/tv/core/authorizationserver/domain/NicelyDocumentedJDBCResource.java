package ch.tbmelabs.tv.core.authorizationserver.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.Data;

@Data
@MappedSuperclass
public class NicelyDocumentedJDBCResource implements Serializable {
  @Transient
  private static final long serialVersionUID = 1L;

  @Transient
  public static final String SEQUENCE_GENERATOR_STRATEGY = "org.hibernate.id.enhanced.SequenceStyleGenerator";

  @NotNull
  @Temporal(TemporalType.TIMESTAMP)
  public Date created;

  @NotNull
  @Column(name = "last_updated")
  @Temporal(TemporalType.TIMESTAMP)
  public Date lastUpdated;

  protected NicelyDocumentedJDBCResource() {
    created = new Date();
    lastUpdated = new Date();
  }

  @PrePersist
  public void onCreate() {
    created = new Date();
    lastUpdated = new Date();
  }

  @PreUpdate
  public void onUpdate() {
    lastUpdated = new Date();
  }

  public static Pageable setLimit(Optional<Integer> limit) {
    return new PageRequest(0, limit.isPresent() ? limit.get() : Integer.MAX_VALUE);
  }
}