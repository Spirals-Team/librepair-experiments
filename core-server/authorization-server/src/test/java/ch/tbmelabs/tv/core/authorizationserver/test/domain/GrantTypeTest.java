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

import ch.tbmelabs.tv.core.authorizationserver.domain.GrantType;
import ch.tbmelabs.tv.core.authorizationserver.domain.NicelyDocumentedJDBCResource;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociation;

public class GrantTypeTest {
  private static final String TEST_GRANT_TYPE_NAME = "TEST";

  @Spy
  private GrantType fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);
  }

  @Test
  public void grantTypeShouldBeAnnotated() {
    assertThat(GrantType.class).hasAnnotation(Entity.class).hasAnnotation(Table.class).hasAnnotation(JsonInclude.class)
        .hasAnnotation(JsonIgnoreProperties.class);

    assertThat(GrantType.class.getDeclaredAnnotation(Table.class).name()).isNotNull().isEqualTo("client_grant_types");
    assertThat(GrantType.class.getDeclaredAnnotation(JsonInclude.class).value()).isNotNull()
        .isEqualTo(Include.NON_NULL);
    assertThat(GrantType.class.getDeclaredAnnotation(JsonIgnoreProperties.class).ignoreUnknown()).isNotNull().isTrue();
  }

  @Test
  public void grantTypeShouldExtendNicelyDocumentedJDBCResource() {
    assertThat(NicelyDocumentedJDBCResource.class).isAssignableFrom(GrantType.class);
  }

  @Test
  public void grantTypeShouldHaveNoArgsConstructor() {
    assertThat(new GrantType()).isNotNull();
  }

  @Test
  public void grantTypeShouldHaveAllArgsConstructor() {
    assertThat(new GrantType(TEST_GRANT_TYPE_NAME)).hasFieldOrPropertyWithValue("name", TEST_GRANT_TYPE_NAME);
  }

  @Test
  public void grantTypeShouldHaveIdGetterAndSetter() {
    Long id = new Random().nextLong();

    fixture.setId(id);

    assertThat(fixture).hasFieldOrPropertyWithValue("id", id);
    assertThat(fixture.getId()).isEqualTo(id);
  }

  @Test
  public void grantTypeShouldHaveNameGetterAndSetter() {
    String name = RandomStringUtils.random(11);

    fixture.setName(name);

    assertThat(fixture).hasFieldOrPropertyWithValue("name", name);
    assertThat(fixture.getName()).isEqualTo(name);
  }

  @Test
  public void grantTypeShouldHaveClientsGetterAndSetter() {
    List<ClientGrantTypeAssociation> associations = Arrays.asList(Mockito.mock(ClientGrantTypeAssociation.class));

    fixture.setClientsWithGrantTypes(associations);

    assertThat(fixture).hasFieldOrPropertyWithValue("clientsWithGrantTypes", associations);
    assertThat(fixture.getClientsWithGrantTypes()).isEqualTo(associations);
  }
}