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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ch.tbmelabs.tv.core.authorizationserver.domain.NicelyDocumentedJDBCResource;
import ch.tbmelabs.tv.core.authorizationserver.domain.Scope;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope.ClientScopeAssociation;

public class ScopeTest {
  private static final String TEST_SCOPE_NAME = "TEST";

  @Spy
  private Scope fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);
  }

  @Test
  public void scopeShouldBeAnnotated() {
    assertThat(Scope.class).hasAnnotation(Entity.class).hasAnnotation(Table.class).hasAnnotation(JsonInclude.class)
        .hasAnnotation(JsonIgnoreProperties.class);

    assertThat(Scope.class.getDeclaredAnnotation(Table.class).name()).isNotNull().isEqualTo("client_scopes");
    assertThat(Scope.class.getDeclaredAnnotation(JsonInclude.class).value()).isNotNull().isEqualTo(Include.NON_NULL);
    assertThat(Scope.class.getDeclaredAnnotation(JsonIgnoreProperties.class).ignoreUnknown()).isNotNull().isTrue();
  }

  @Test
  public void scopeShouldExtendNicelyDocumentedJDBCResource() {
    assertThat(NicelyDocumentedJDBCResource.class).isAssignableFrom(Scope.class);
  }

  @Test
  public void scopeShouldHaveNoArgsConstructor() {
    assertThat(new Scope()).isNotNull();
  }

  @Test
  public void scopeShouldHaveAllArgsConstructor() {
    assertThat(new Scope(TEST_SCOPE_NAME)).hasFieldOrPropertyWithValue("name", TEST_SCOPE_NAME);
  }

  @Test
  public void scopeTypeShouldHaveIdGetterAndSetter() {
    Long id = new Random().nextLong();

    fixture.setId(id);

    assertThat(fixture).hasFieldOrPropertyWithValue("id", id);
    assertThat(fixture.getId()).isEqualTo(id);
  }

  @Test
  public void scopeShouldHaveNameGetterAndSetter() {
    String name = RandomStringUtils.random(11);

    fixture.setName(name);

    assertThat(fixture).hasFieldOrPropertyWithValue("name", name);
    assertThat(fixture.getName()).isEqualTo(name);
  }

  @Test
  public void grantTypeShouldHaveClientsGetterAndSetter() {
    List<ClientScopeAssociation> associations = Arrays.asList(Mockito.mock(ClientScopeAssociation.class));

    fixture.setClientsWithScopes(associations);

    assertThat(fixture).hasFieldOrPropertyWithValue("clientsWithScopes", associations);
    assertThat(fixture.getClientsWithScopes()).isEqualTo(associations);
  }
}