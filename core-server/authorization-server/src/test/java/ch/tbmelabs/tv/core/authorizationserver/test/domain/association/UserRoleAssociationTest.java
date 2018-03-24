package ch.tbmelabs.tv.core.authorizationserver.test.domain.association;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Random;

import javax.persistence.IdClass;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociationId;

public class UserRoleAssociationTest {
  @Mock
  private User userFixture;

  @Mock
  private Role roleFixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(new Random().nextLong()).when(userFixture).getId();
    doReturn(new Random().nextLong()).when(roleFixture).getId();
  }

  @Test
  public void userRoleAssociationShouldBeAnnotatedWithComposedIdClass() {
    assertThat(UserRoleAssociation.class).hasAnnotation(IdClass.class);
    assertThat(UserRoleAssociation.class.getDeclaredAnnotation(IdClass.class).value()).isNotNull()
        .isEqualTo(UserRoleAssociationId.class);
  }

  @Test
  public void userRoleAssociationShouldHaveNoArgsConstructor() {
    assertThat(new UserRoleAssociation()).isNotNull();
  }

  @Test
  public void userRoleAssociationShouldHaveAllArgsConstructor() {
    assertThat(new UserRoleAssociation(userFixture, roleFixture))
        .hasFieldOrPropertyWithValue("userId", userFixture.getId())
        .hasFieldOrPropertyWithValue("userRoleId", roleFixture.getId()).hasFieldOrPropertyWithValue("user", userFixture)
        .hasFieldOrPropertyWithValue("userRole", roleFixture);
  }

  @Test
  public void userRoleAssociationShouldHaveUserGetterAndSetter() {
    UserRoleAssociation fixture = new UserRoleAssociation();
    fixture.setUser(userFixture);

    assertThat(fixture).hasFieldOrPropertyWithValue("userId", userFixture.getId()).hasFieldOrPropertyWithValue("user",
        userFixture);
    assertThat(fixture.getUser()).isEqualTo(userFixture);
  }

  @Test
  public void userRoleAssociationShouldHaveRoleGetterAndSetter() {
    UserRoleAssociation fixture = new UserRoleAssociation();
    fixture.setUserRole(roleFixture);

    assertThat(fixture).hasFieldOrPropertyWithValue("userRoleId", roleFixture.getId())
        .hasFieldOrPropertyWithValue("userRole", roleFixture);
    assertThat(fixture.getUserRole()).isEqualTo(roleFixture);
  }
}