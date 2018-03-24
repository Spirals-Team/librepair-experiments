package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import ch.tbmelabs.tv.core.authorizationserver.configuration.DatasourceConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class DatasourceConfigurationIntTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  @Autowired
  private DatasourceConfiguration configuration;

  @Autowired
  private DataSource bean;

  @Autowired
  @Qualifier("jdbcTokenStoreDatasource")
  private DataSource secondaryBean;

  @Test
  public void primaryRegisteredBeanShouldEqualConfiguredDataSource() {
    assertThat(configuration.dataSource()).isEqualTo(bean);
  }

  @Test
  public void secondaryBeanShouldBeInitialized() {
    assertThat(configuration.jdbcTokenStoreDatasource()).isEqualTo(secondaryBean);
  }
}