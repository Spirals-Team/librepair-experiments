package lv.robertsv.webjob;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@EnableJpaRepositories(basePackages = { "lv.robertsv.webjob" })
@EnableTransactionManagement
public class WebJobsJpaConfig implements DisposableBean {

	private EmbeddedDatabase ed;

    @Bean
    public DataSource dataSource() {

        BasicDataSource dataSourceConfig = new BasicDataSource();
        dataSourceConfig.setDriverClassName("org.postgresql.Driver");
        dataSourceConfig.setUrl("jdbc:postgresql://db:5432/postgres");
        dataSourceConfig.setUsername("postgres");
        dataSourceConfig.setValidationQuery("SELECT 1");
        dataSourceConfig.setPassword("password");

        return dataSourceConfig;
    }

	@Bean
	public EntityManagerFactory entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("lv.robertsv.webjob");
		factory.setDataSource(this.dataSource());

		Properties ps = new Properties();
		ps.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		ps.put("hibernate.hbm2ddl.auto", "create-drop");
		factory.setJpaProperties(ps);
		factory.afterPropertiesSet();

		return factory.getObject();
	}

	@Override
	public void destroy() {
		if (this.ed != null) {
			this.ed.shutdown();
		}
	}

}
