package pro.taskana.rest;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.SpringHandlerInstantiator;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;

import pro.taskana.ClassificationService;
import pro.taskana.TaskMonitorService;
import pro.taskana.TaskService;
import pro.taskana.TaskanaEngine;
import pro.taskana.WorkbasketService;
import pro.taskana.configuration.SpringTaskanaEngineConfiguration;
import pro.taskana.configuration.TaskanaEngineConfiguration;
import pro.taskana.ldap.LdapClient;

/**
 * Configuration for REST service.
 */
@Configuration
@ComponentScan
@EnableTransactionManagement
public class RestConfiguration {

    @Autowired
    private Environment env;

    @Bean
    public LdapContextSource contextSource() {

        LdapContextSource contextSource = new LdapContextSource();
        boolean useLdap;
        String useLdapConfigValue = env.getProperty("taskana.ldap.useLdap");
        if (useLdapConfigValue == null || useLdapConfigValue.isEmpty()) {
            useLdap = false;
        } else {
            useLdap = Boolean.parseBoolean(useLdapConfigValue);
        }
        if (useLdap) {
            contextSource.setUrl(env.getRequiredProperty("taskana.ldap.serverUrl"));
            contextSource.setBase(env.getRequiredProperty("taskana.ldap.baseDn"));
            contextSource.setUserDn(env.getRequiredProperty("taskana.ldap.bindDn"));
            contextSource.setPassword(env.getRequiredProperty("taskana.ldap.bindPassword"));
        } else {
            contextSource.setUrl("ldap://com.dummy:9999");
            contextSource.setBase("o=taskana");
            contextSource.setUserDn("user");
            contextSource.setPassword("secret");
        }
        return contextSource;
    }

    @Bean
    public LdapClient ldapClient() {
        return new LdapClient();
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource());
    }

    @Bean
    public ClassificationService getClassificationService(TaskanaEngine taskanaEngine) {
        return taskanaEngine.getClassificationService();
    }

    @Bean
    public TaskService getTaskService(TaskanaEngine taskanaEngine) {
        return taskanaEngine.getTaskService();
    }

    @Bean
    public TaskMonitorService getTaskMonitorService(TaskanaEngine taskanaEngine) {
        return taskanaEngine.getTaskMonitorService();
    }

    @Bean
    public WorkbasketService getWorkbasketService(TaskanaEngine taskanaEngine) {
        return taskanaEngine.getWorkbasketService();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public TaskanaEngine getTaskanaEngine(TaskanaEngineConfiguration taskanaEngineConfiguration) {
        return taskanaEngineConfiguration.buildTaskanaEngine();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public TaskanaEngineConfiguration taskanaEngineConfiguration(DataSource dataSource) throws SQLException {
        return new SpringTaskanaEngineConfiguration(dataSource, true, true);
    }

    // Needed to override JSON De-/Serializer in Jackson.
    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder(HandlerInstantiator handlerInstantiator) {
        Jackson2ObjectMapperBuilder b = new Jackson2ObjectMapperBuilder();
        b.indentOutput(true);
        b.handlerInstantiator(handlerInstantiator);
        return b;
    }

    // Needed for injection into jackson deserializer.
    @Bean
    public HandlerInstantiator handlerInstantiator(ApplicationContext context) {
        return new SpringHandlerInstantiator(context.getAutowireCapableBeanFactory());
    }
}
