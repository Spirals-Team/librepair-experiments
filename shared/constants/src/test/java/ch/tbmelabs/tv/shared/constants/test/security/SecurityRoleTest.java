package ch.tbmelabs.tv.shared.constants.test.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.Test;

import ch.tbmelabs.tv.shared.constants.security.SecurityRole;

public class SecurityRoleTest {
  private static final String GANDALF = "GANDALF";
  private static final String SERVER_ADMIN = "SERVER_ADMIN";
  private static final String SERVER_SUPPORT = "SERVER_SUPPORT";
  private static final String CONTENT_ADMIN = "CONTENT_ADMIN";
  private static final String CONTENT_SUPPORT = "CONTENT_SUPPORT";
  private static final String PREMIUM_USER = "PREMIUM_USER";
  private static final String USER = "USER";
  private static final String GUEST = "GUEST";
  private static final String ANONYMOUS = "ANONYMOUS";

  @Test
  public void securityRolesShouldBePublicStatic() {
    assertThat(SecurityRole.GANDALF).isEqualTo(GANDALF);
    assertThat(SecurityRole.SERVER_ADMIN).isEqualTo(SERVER_ADMIN);
    assertThat(SecurityRole.SERVER_SUPPORT).isEqualTo(SERVER_SUPPORT);
    assertThat(SecurityRole.CONTENT_ADMIN).isEqualTo(CONTENT_ADMIN);
    assertThat(SecurityRole.CONTENT_SUPPORT).isEqualTo(CONTENT_SUPPORT);
    assertThat(SecurityRole.PREMIUM_USER).isEqualTo(PREMIUM_USER);
    assertThat(SecurityRole.USER).isEqualTo(USER);
    assertThat(SecurityRole.GUEST).isEqualTo(GUEST);
    assertThat(SecurityRole.ANONYMOUS).isEqualTo(ANONYMOUS);
  }

  @Test
  public void staticHolderClassShouldNotHaveAnyAccessableConstructor() throws NoSuchMethodException, SecurityException,
      InstantiationException, IllegalAccessException, InvocationTargetException {
    Constructor<SecurityRole> fixture = SecurityRole.class.getDeclaredConstructor(new Class<?>[] {});
    fixture.setAccessible(true);

    assertThat(Modifier.isPrivate(fixture.getModifiers())).isTrue();
    assertThat(fixture.newInstance(new Object[] {})).isNotNull();
  }
}