package uo.asw;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ManagerApplication {

	public static final Logger logger = Logger.getLogger(ManagerApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(ManagerApplication.class, args);
	}

}