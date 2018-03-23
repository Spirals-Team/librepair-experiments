package ch.tbmelabs.tv.core.authorizationserver.configuration;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@Configuration
public class DatasourceConfiguration {
  private static final Logger LOGGER = LogManager.getLogger(DatasourceConfiguration.class);

  @Bean
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean
  @Profile({ SpringApplicationProfile.NO_REDIS })
  @ConfigurationProperties(prefix = "tokenstore.datasource")
  public DataSource jdbcTokenStoreDatasource() {
    LOGGER.warn("Profile \"" + SpringApplicationProfile.NO_REDIS + "\" is active: tokenstore will be of type "
        + JdbcTokenStore.class);

    return DataSourceBuilder.create().build();
  }
}