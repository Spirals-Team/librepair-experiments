package ch.tbmelabs.tv.core.authorizationserver.test.domain.association;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import org.junit.Test;

import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociationId;

public class UserRoleAssociationIdTest {
  @Test
  public void userRoleAssociationIdShouldHaveNoArgsConstructor() {
    assertThat(new UserRoleAssociationId()).isNotNull();
  }

  @Test
  public void userRoleAssociationIdShouldHaveUserIdGetterAndSetter() {
    UserRoleAssociationId fixture = new UserRoleAssociationId();
    Long userId = new Random().nextLong();

    fixture.setUserId(userId);

    assertThat(fixture).hasFieldOrPropertyWithValue("userId", userId);
    assertThat(fixture.getUserId()).isEqualTo(userId);
  }

  @Test
  public void userRoleAssociationIdShouldHaveUserRoleIdGetterAndSetter() {
    UserRoleAssociationId fixture = new UserRoleAssociationId();
    Long userRoleId = new Random().nextLong();

    fixture.setUserRoleId(userRoleId);

    assertThat(fixture).hasFieldOrPropertyWithValue("userRoleId", userRoleId);
    assertThat(fixture.getUserRoleId()).isEqualTo(userRoleId);
  }
}