package ch.tbmelabs.tv.core.authorizationserver.test.domain.association;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import org.junit.Test;

import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope.ClientScopeAssociationId;

public class ClientScopeAssociationIdTest {
  @Test
  public void clientScopeAssociationIdShouldHaveNoArgsConstructor() {
    assertThat(new ClientScopeAssociationId()).isNotNull();
  }

  @Test
  public void clientScopeAssociationIdShouldHaveClientIdGetterAndSetter() {
    ClientScopeAssociationId fixture = new ClientScopeAssociationId();
    Long clientId = new Random().nextLong();

    fixture.setClientId(clientId);

    assertThat(fixture).hasFieldOrPropertyWithValue("clientId", clientId);
    assertThat(fixture.getClientId()).isEqualTo(clientId);
  }

  @Test
  public void clientAuthorityAssociationIdShouldHaveAuthorityIdGetterAndSetter() {
    ClientScopeAssociationId fixture = new ClientScopeAssociationId();
    Long clientScopeId = new Random().nextLong();

    fixture.setClientScopeId(clientScopeId);

    assertThat(fixture).hasFieldOrPropertyWithValue("clientScopeId", clientScopeId);
    assertThat(fixture.getClientScopeId()).isEqualTo(clientScopeId);
  }
}