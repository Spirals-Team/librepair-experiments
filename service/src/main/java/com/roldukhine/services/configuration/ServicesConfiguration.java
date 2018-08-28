package com.roldukhine.services.configuration;

import com.roldukhine.configuration.DaoConfiguration;
import com.roldukhine.configuration.JdbcConfiguration;
import com.roldukhine.configuration.JpaConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DaoConfiguration.class, JdbcConfiguration.class, JpaConfiguration.class})
@ComponentScan("com.roldukhine.entity")
public class ServicesConfiguration {

}
