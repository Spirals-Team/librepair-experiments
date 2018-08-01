package net.posesor;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackageClasses = MongoConfiguration.class)
public class MongoConfiguration {

    @Bean
    MongoTemplate mongoTemplate() throws Exception {
        String ip = "localhost";
        int port = 27017;

        return new MongoTemplate(new MongoClient(ip, port), "test");
    }
}
