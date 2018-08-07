package com.hedvig.productPricing.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWS {

    @Bean
    public NotificationMessagingTemplate notificationTemplate(AmazonSNS amazonSNS) {
        return new NotificationMessagingTemplate(amazonSNS);
    }

    @Bean
    AWSCredentialsProvider credentialsProvider() {
        return new DefaultAWSCredentialsProviderChain();
    }

    @Bean
    public AmazonSimpleEmailService amazonSimpleEmailService(AWSCredentialsProvider credentialsProvider) {
        return AmazonSimpleEmailServiceClientBuilder.standard()
            .withCredentials(credentialsProvider)
            .withRegion(Regions.EU_WEST_1).build();
    }

    @Bean
    AmazonS3 s3Client(AWSCredentialsProvider credentialsProvider) {
        return AmazonS3Client.builder().withCredentials(credentialsProvider).build();
    }
}
