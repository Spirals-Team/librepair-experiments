package com.cmpl.web.configuration.manager.privileges;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({WebmasteringPrivilegesConfiguration.class, AdministrationPrivilegesConfiguration.class,
    IndexPrivilegesConfiguration.class})
public class PrivilegesConfiguration {

}
