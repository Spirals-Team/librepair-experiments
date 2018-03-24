package ch.tbmelabs.tv.core.authorizationserver.test.domain.association;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;
import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociationId;

public class ClientAuthorityAssociationTest {
  @Mock
  private Client clientFixture;

  @Mock
  private Authority authorityFixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(new Random().nextLong()).when(clientFixture).getId();
    doReturn(new Random().nextLong()).when(authorityFixture).getId();
  }

  @Test
  public void clientAuthorityAssociationShouldBeAnnotated() {
    assertThat(ClientAuthorityAssociation.class).hasAnnotation(Entity.class).hasAnnotation(Table.class)
        .hasAnnotation(JsonInclude.class).hasAnnotation(JsonIgnoreProperties.class);

    assertThat(ClientAuthorityAssociation.class.getDeclaredAnnotation(Table.class).name()).isNotNull()
        .isEqualTo("client_has_authorities");
    assertThat(ClientAuthorityAssociation.class.getDeclaredAnnotation(JsonInclude.class).value()).isNotNull()
        .isEqualTo(Include.NON_NULL);
    assertThat(ClientAuthorityAssociation.class.getDeclaredAnnotation(JsonIgnoreProperties.class).ignoreUnknown())
        .isNotNull().isTrue();
  }

  @Test
  public void clientAuthorityAssociationShouldBeAnnotatedWithComposedIdClass() {
    assertThat(ClientAuthorityAssociation.class).hasAnnotation(IdClass.class);
    assertThat(ClientAuthorityAssociation.class.getDeclaredAnnotation(IdClass.class).value()).isNotNull()
        .isEqualTo(ClientAuthorityAssociationId.class);
  }

  @Test
  public void clientAuthorityAssociationShouldHaveNoArgsConstructor() {
    assertThat(new ClientAuthorityAssociation()).isNotNull();
  }

  @Test
  public void clientAuthorityAssociationShouldHaveAllArgsConstructor() {
    assertThat(new ClientAuthorityAssociation(clientFixture, authorityFixture))
        .hasFieldOrPropertyWithValue("clientId", clientFixture.getId())
        .hasFieldOrPropertyWithValue("clientAuthorityId", authorityFixture.getId())
        .hasFieldOrPropertyWithValue("client", clientFixture)
        .hasFieldOrPropertyWithValue("clientAuthority", authorityFixture);
  }

  @Test
  public void clientAuthorityAssociationShouldHaveClientGetterAndSetter() {
    ClientAuthorityAssociation fixture = new ClientAuthorityAssociation();
    fixture.setClient(clientFixture);

    assertThat(fixture).hasFieldOrPropertyWithValue("clientId", clientFixture.getId())
        .hasFieldOrPropertyWithValue("client", clientFixture);
    assertThat(fixture.getClient()).isEqualTo(clientFixture);
  }

  @Test
  public void clientAuthorityAssociationShouldHaveAuthorityGetterAndSetter() {
    ClientAuthorityAssociation fixture = new ClientAuthorityAssociation();
    fixture.setClientAuthority(authorityFixture);

    assertThat(fixture).hasFieldOrPropertyWithValue("clientAuthorityId", authorityFixture.getId())
        .hasFieldOrPropertyWithValue("clientAuthority", authorityFixture);
    assertThat(fixture.getClientAuthority()).isEqualTo(authorityFixture);
  }
}