package ch.tbmelabs.tv.core.authorizationserver.test.domain.association;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import org.junit.Test;

import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociationId;

public class ClientAuthorityAssociationIdTest {
  @Test
  public void clientAuthorityAssociationIdShouldHaveNoArgsConstructor() {
    assertThat(new ClientAuthorityAssociationId()).isNotNull();
  }

  @Test
  public void clientAuthorityAssociationIdShouldHaveClientIdGetterAndSetter() {
    ClientAuthorityAssociationId fixture = new ClientAuthorityAssociationId();
    Long clientId = new Random().nextLong();

    fixture.setClientId(clientId);

    assertThat(fixture).hasFieldOrPropertyWithValue("clientId", clientId);
    assertThat(fixture.getClientId()).isEqualTo(clientId);
  }

  @Test
  public void clientAuthorityAssociationIdShouldHaveAuthorityIdGetterAndSetter() {
    ClientAuthorityAssociationId fixture = new ClientAuthorityAssociationId();
    Long clientAuthorityId = new Random().nextLong();

    fixture.setClientAuthorityId(clientAuthorityId);

    assertThat(fixture).hasFieldOrPropertyWithValue("clientAuthorityId", clientAuthorityId);
    assertThat(fixture.getClientAuthorityId()).isEqualTo(clientAuthorityId);
  }
}