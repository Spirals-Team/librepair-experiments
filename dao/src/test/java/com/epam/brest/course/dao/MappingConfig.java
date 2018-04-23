package com.epam.brest.course.dao;

import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

/**
 * config class for dozer.
 */
@Configuration
public class MappingConfig {
    /**
     *
     * @param resources to find resources.
     * @return dozer.
     * @throws Exception exception.
     */

    @Bean
    public DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean(@Value
                        ("classpath*:mapping.xml") final Resource[] resources)
                                                             throws Exception {
        final DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean =
                                              new DozerBeanMapperFactoryBean();

         dozerBeanMapperFactoryBean.setMappingFiles(resources);
        return dozerBeanMapperFactoryBean;
    }

}
