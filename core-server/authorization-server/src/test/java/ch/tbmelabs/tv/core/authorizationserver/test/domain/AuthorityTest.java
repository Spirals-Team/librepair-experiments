package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociation;

public class AuthorityTest {
  private static final String TEST_AUTHORITY_NAME = "TEST";

  @Spy
  private Authority fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);
  }

  @Test
  public void roleShouldBeAnnotated() {
    assertThat(Authority.class).hasAnnotation(Entity.class).hasAnnotation(Table.class).hasAnnotation(JsonInclude.class)
        .hasAnnotation(JsonIgnoreProperties.class);

    assertThat(Authority.class.getDeclaredAnnotation(Table.class).name()).isNotNull().isEqualTo("client_authorities");
    assertThat(Authority.class.getDeclaredAnnotation(JsonInclude.class).value()).isNotNull()
        .isEqualTo(Include.NON_NULL);
    assertThat(Authority.class.getDeclaredAnnotation(JsonIgnoreProperties.class).ignoreUnknown()).isNotNull().isTrue();
  }

  @Test
  public void authorityShouldHaveNoArgsConstructor() {
    assertThat(new Authority()).isNotNull();
  }

  @Test
  public void authorityShouldHaveAllArgsConstructor() {
    assertThat(new Authority(TEST_AUTHORITY_NAME)).hasFieldOrPropertyWithValue("name", TEST_AUTHORITY_NAME);
  }

  @Test
  public void authorityShouldHaveIdGetterAndSetter() {
    Long id = new Random().nextLong();

    fixture.setId(id);

    assertThat(fixture).hasFieldOrPropertyWithValue("id", id);
    assertThat(fixture.getId()).isEqualTo(id);
  }

  @Test
  public void authorityShouldHaveNameGetterAndSetter() {
    String name = RandomStringUtils.random(11);

    fixture.setName(name);

    assertThat(fixture).hasFieldOrPropertyWithValue("name", name);
    assertThat(fixture.getName()).isEqualTo(name);
  }

  @Test
  public void authorityShouldHaveClientsGetterAndSetter() {
    List<ClientAuthorityAssociation> associations = Arrays.asList(Mockito.mock(ClientAuthorityAssociation.class));

    fixture.setClientsWithAuthorities(associations);

    assertThat(fixture).hasFieldOrPropertyWithValue("clientsWithAuthorities", associations);
    assertThat(fixture.getClientsWithAuthorities()).isEqualTo(associations);
  }

  @Test
  public void getAuthorityShouldReturnSecurityRole() {
    ReflectionTestUtils.setField(fixture, "name", TEST_AUTHORITY_NAME);

    assertThat(fixture.getAuthority()).isEqualTo("ROLE_" + TEST_AUTHORITY_NAME);
  }
}