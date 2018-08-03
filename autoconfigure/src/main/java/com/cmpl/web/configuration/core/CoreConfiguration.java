package com.cmpl.web.configuration.core;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.cmpl.web.configuration.core.common.CommonConfiguration;
import com.cmpl.web.configuration.core.scheduler.SchedulerConfiguration;

@Configuration
@Import({CommonConfiguration.class, AdministrationConfiguration.class, WebmasteringConfiguration.class,
    SchedulerConfiguration.class, FileConfiguration.class})
public class CoreConfiguration {

}
