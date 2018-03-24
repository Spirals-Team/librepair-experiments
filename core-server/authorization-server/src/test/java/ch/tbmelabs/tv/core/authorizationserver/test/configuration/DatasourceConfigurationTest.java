package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import ch.tbmelabs.tv.core.authorizationserver.configuration.DatasourceConfiguration;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class DatasourceConfigurationTest {
  private static final String PRIMARY_DATASOURCE_PREFIX = "spring.datasource";
  private static final String JDBC_TOKEN_STORE_DATASOURCE_PREFIX = "tokenstore.datasource";

  @Test
  public void dataSourceConfigurationShouldBeAnnotated() {
    assertThat(DatasourceConfiguration.class).hasAnnotation(Configuration.class);
  }

  @Test
  public void dataSourceConfigurationShouldHavePublicConstructor() {
    assertThat(new DatasourceConfiguration()).isNotNull();
  }

  @Test
  public void springDataSourceShouldBeAnnotatedAsPrimaryBean() throws NoSuchMethodException, SecurityException {
    Method datasourceConfiguration = DatasourceConfiguration.class.getDeclaredMethod("dataSource", new Class[] {});

    assertThat(datasourceConfiguration.getDeclaredAnnotation(Bean.class)).isNotNull();
    assertThat(datasourceConfiguration.getDeclaredAnnotation(Primary.class)).isNotNull();
    assertThat(datasourceConfiguration.getDeclaredAnnotation(ConfigurationProperties.class).prefix())
        .isEqualTo(PRIMARY_DATASOURCE_PREFIX);
  }

  @Test
  public void jdbcTokenStoreShouldNotOccurInProductiveEnvironment() throws NoSuchMethodException, SecurityException {
    Method jdbcTokenStoreDatasourceConfiguration = DatasourceConfiguration.class
        .getDeclaredMethod("jdbcTokenStoreDatasource", new Class[] {});

    assertThat(jdbcTokenStoreDatasourceConfiguration.getDeclaredAnnotation(Bean.class)).isNotNull();
    assertThat(jdbcTokenStoreDatasourceConfiguration.getDeclaredAnnotation(Profile.class).value())
        .containsExactly(SpringApplicationProfile.NO_REDIS);
    assertThat(jdbcTokenStoreDatasourceConfiguration.getDeclaredAnnotation(ConfigurationProperties.class).prefix())
        .isEqualTo(JDBC_TOKEN_STORE_DATASOURCE_PREFIX);
  }
}