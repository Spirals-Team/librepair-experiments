package dev.repositories.question;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
// Activer Spring Data JPA et indiquer les packages où se trouvent les
// interfaces
@EnableJpaRepositories("dev.repositories")
public class OptionQuestionAppConfig {

	// Bean indispensable pour faire du JPA
	@Bean
	public EntityManagerFactory entityManagerFactory(DataSource ds) {

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

		// (...) configuration de l'EMF
		factory.setDataSource(ds);

		factory.afterPropertiesSet();
		return factory.getObject();
	}

	@Bean // gestionnaire de transaction
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(emf);
		return txManager;
	}

}
