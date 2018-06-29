package com.hedvig.productPricing.service.configuration;

import com.hedvig.productPricing.service.service.InsuranceLookup;
import com.hedvig.productPricing.service.service.InsuranceLookupProduction;
import com.hedvig.productPricing.service.service.InsuranceLookupTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class Email {

    Logger log = LoggerFactory.getLogger(Email.class);

    @Bean
    @Profile("production")
    public InsuranceLookup insuranceLookup(){
        log.info("Using InsuranceLookup for PRODUCTION");
        return new InsuranceLookupProduction();
    }

    @Bean
    @Profile("!production")
    public InsuranceLookup insuranceLookupTest(){
        log.info("Using InsuranceLookup TEST");
        return new InsuranceLookupTest();
    }

}
