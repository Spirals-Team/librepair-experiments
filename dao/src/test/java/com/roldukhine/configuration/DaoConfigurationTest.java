package com.roldukhine.configuration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.Isolation;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:connection.properties")
public class DaoConfigurationTest {

    @Value("${db.driver}")
    private String driverClassName;

    @Value("${db.url}")
    private String url;

    @Value("${db.username}")
    private String dbUsername;

    @Value("${db.password}")
    private String password;

    @Value("classpath:h2_create_data_model.sql")
    private Resource dataScriptModel;

    @Value("classpath:create-data-model.sql")
    private Resource dataScriptData;

    @Bean(destroyMethod = "")
    public BasicDataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(driverClassName);
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(dbUsername);
        basicDataSource.setPassword(password);
        basicDataSource.setDefaultAutoCommit(true);
        basicDataSource.setDefaultTransactionIsolation(Isolation.READ_COMMITTED.value());
        return basicDataSource;
    }

    /**
     * Property placeholder configurer needed to process @Value annotations
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(dataScriptModel);
        populator.addScript(dataScriptData);
        return populator;
    }

}
