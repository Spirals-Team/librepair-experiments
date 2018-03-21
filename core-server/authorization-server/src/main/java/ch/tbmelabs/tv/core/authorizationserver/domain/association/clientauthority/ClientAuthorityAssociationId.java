package ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientAuthorityAssociationId implements Serializable {
  private static final long serialVersionUID = 1L;

  private Long clientId;
  private Long clientAuthorityId;
}