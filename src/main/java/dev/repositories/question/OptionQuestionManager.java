package dev.repositories.question;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//activation du support de l'annotation @Transactional
@EnableTransactionManagement
public class OptionQuestionManager {

	@Bean
	public PlatformTransactionManager txManager(DataSource datasource) {
		return new DataSourceTransactionManager(datasource);
	}

}
