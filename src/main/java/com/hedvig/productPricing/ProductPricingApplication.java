package com.hedvig.productPricing;

import com.hedvig.productPricing.pricing.PricingEngine;
import com.hedvig.productPricing.pricing.PricingEngineHedvig_v1;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients()
public class ProductPricingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductPricingApplication.class, args);
	}

    @Bean
    public PricingEngine pricingEngine(@Value("${hedvig.product-pricing.files.location}") String fileLocation){
    	return new PricingEngineHedvig_v1(fileLocation);
    }


}
