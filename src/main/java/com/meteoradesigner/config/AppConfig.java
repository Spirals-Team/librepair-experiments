package com.meteoradesigner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

//TODO fix all the documentation, by using this class, -es and dots.
@Configuration
@ComponentScan(basePackages = "com.meteoradesigner.**")
@EnableJpaRepositories("com.meteoradesigner.repository")
@EnableTransactionManagement
public class AppConfig {

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter bean = new HibernateJpaVendorAdapter();
        bean.setDatabase(Database.H2);
        bean.setGenerateDdl(true);
        return bean;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource);
        bean.setJpaVendorAdapter(jpaVendorAdapter);
        bean.setPackagesToScan("com.meteoradesigner");
        bean.setJpaProperties(hibernateProperties());
        return bean;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put(org.hibernate.cfg.Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
        return properties;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DataSource dataSource = createDataSource();
        DatabasePopulatorUtils.execute(databasePopulator(), dataSource);
        return dataSource;
    }

    private DatabasePopulator databasePopulator() {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.setContinueOnError(true);
        databasePopulator.addScript(new ClassPathResource("db/H2InitDB.sql"));
        databasePopulator.addScript(new ClassPathResource("db/H2PopulateDB.sql"));
        return databasePopulator;
    }

    //TODO how long virtual machine is alive
    //TODO metadata to log before the commit call return
    private SimpleDriverDataSource createDataSource() {
        SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource();
        simpleDriverDataSource.setDriverClass(org.h2.Driver.class);
        simpleDriverDataSource.setUrl("jdbc:h2:mem:test;LOCK_MODE=1;DB_CLOSE_DELAY=-1;" +
                "ACCESS_MODE_DATA=rws;PAGE_SIZE=4");
        simpleDriverDataSource.setUsername("");
        simpleDriverDataSource.setPassword("");
        return simpleDriverDataSource;
    }
}