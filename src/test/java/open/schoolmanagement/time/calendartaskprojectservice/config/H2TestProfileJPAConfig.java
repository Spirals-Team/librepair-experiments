package open.schoolmanagement.time.calendartaskprojectservice.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "open.schoolmanagement.time.calendartaskprojectservice.repository")
@PropertySource("persistence-generic-entity.properties")
@EnableTransactionManagement
public class H2TestProfileJPAConfig {

  @Bean
  @Profile("test")
  public DataSource dataSource() {

    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.h2.Driver");
    dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
    dataSource.setUsername("osm");
    dataSource.setPassword("osm");

    return dataSource;
  }
}
