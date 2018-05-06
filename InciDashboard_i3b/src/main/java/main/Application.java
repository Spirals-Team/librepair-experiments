package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import repository.IncidenceRepository;

@SpringBootApplication
@ComponentScan({"controller", "kafka"})
@EnableMongoRepositories(basePackageClasses = IncidenceRepository.class)
public class Application {   
	
    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }

}