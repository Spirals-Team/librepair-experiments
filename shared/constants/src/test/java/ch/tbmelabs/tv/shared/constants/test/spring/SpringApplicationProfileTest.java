package ch.tbmelabs.tv.shared.constants.test.spring;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.Test;

import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class SpringApplicationProfileTest {
  public static final String PROD = "prod";
  public static final String DEV = "dev";
  public static final String TEST = "test";
  public static final String ELK = "elk";
  public static final String NO_REDIS = "no-redis";
  public static final String NO_MAIL = "no-mail";

  @Test
  public void springApplicationProfilesShouldBePublicStatic() {
    assertThat(SpringApplicationProfile.PROD).isEqualTo(PROD);
    assertThat(SpringApplicationProfile.DEV).isEqualTo(DEV);
    assertThat(SpringApplicationProfile.TEST).isEqualTo(TEST);
    assertThat(SpringApplicationProfile.NO_REDIS).isEqualTo(NO_REDIS);
    assertThat(SpringApplicationProfile.NO_MAIL).isEqualTo(NO_MAIL);
  }

  @Test
  public void staticHolderClassShouldNotHaveAnyAccessableConstructor() throws NoSuchMethodException, SecurityException,
      InstantiationException, IllegalAccessException, InvocationTargetException {
    Constructor<SpringApplicationProfile> fixture = SpringApplicationProfile.class
        .getDeclaredConstructor(new Class<?>[] {});
    fixture.setAccessible(true);

    assertThat(Modifier.isPrivate(fixture.getModifiers())).isTrue();
    assertThat(fixture.newInstance(new Object[] {})).isNotNull();
  }
}