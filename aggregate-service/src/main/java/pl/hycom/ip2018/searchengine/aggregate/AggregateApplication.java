package pl.hycom.ip2018.searchengine.aggregate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
/**
 * Main for Aggregation Service
 */
public class AggregateApplication {

    public static void main(String[] args) {
        SpringApplication.run(AggregateApplication.class, args);
    }
}
