package ch.tbmelabs.tv.shared.constants.test.oauth2;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.Test;

import ch.tbmelabs.tv.shared.constants.oauth2.ClientGrantType;

public class ClientGrantTypeTest {
  private static final String AUTHORIZATION_CODE = "authorization_code";
  private static final String REFRESH_TOKEN = "refresh_token";
  private static final String IMPLICIT = "implicit";
  private static final String PASSWORD = "password";

  public void clientGrantTypesShouldBePublicStatic() {
    assertThat(ClientGrantType.AUTHORIZATION_CODE).isEqualTo(AUTHORIZATION_CODE);
    assertThat(ClientGrantType.REFRESH_TOKEN).isEqualTo(REFRESH_TOKEN);
    assertThat(ClientGrantType.IMPLICIT).isEqualTo(IMPLICIT);
    assertThat(ClientGrantType.PASSWORD).isEqualTo(PASSWORD);
  }

  @Test
  public void staticHolderClassShouldNotHaveAnyAccessableConstructor() throws NoSuchMethodException, SecurityException,
      InstantiationException, IllegalAccessException, InvocationTargetException {
    Constructor<ClientGrantType> fixture = ClientGrantType.class.getDeclaredConstructor(new Class<?>[] {});
    fixture.setAccessible(true);

    assertThat(Modifier.isPrivate(fixture.getModifiers())).isTrue();
    assertThat(fixture.newInstance(new Object[] {})).isNotNull();
  }
}