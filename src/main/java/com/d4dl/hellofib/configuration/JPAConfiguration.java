package com.d4dl.hellofib.configuration;

import com.d4dl.hellofib.greeting.Greeting;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

/**
 * This configuration ensures that IDs for entities are marshalled to the front end.
 */
@Configuration
public class JPAConfiguration extends RepositoryRestConfigurerAdapter {

    /**
     * Configure entities to expose ids to the front end
     * @param config the auto-injected repository configuration to add classes to for id exposure.
     */
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Greeting.class);
    }

}
