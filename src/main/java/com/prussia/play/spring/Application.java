package com.prussia.play.spring;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;

import com.prussia.play.spring.dao.MyDao;
import com.prussia.play.spring.domain.po.Quote;

@SpringBootApplication
public class Application extends SpringBootServletInitializer implements CommandLineRunner  {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Autowired
	MyDao mydao;
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
		return application.sources(Application.class); //start up in war
	}

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);
		printBeanDef(context);
		printMessageResources(context);
		
	}

	public static void printMessageResources(ApplicationContext context) {
		printMessagebyLocale(context, Locale.CHINA);
		printMessagebyLocale(context, Locale.ENGLISH);
	}

	private static void printMessagebyLocale(ApplicationContext context, Locale local) {
		String message = context.getMessage("message.A", null, "default message", local);
		log.info("message = " + message);
		
		String[] args = {"my", "new"," xxx" };
		String message2 = context.getMessage("argument.required", args, "default message2", local);
		log.info("message = " + message2);
	}

	public void run(String... strings) throws Exception {
		// consumeREST();
		//mydao.accessRelationDBbyTemplate4mysql();
	}

	/**
	 * consume a REST web service
	 */
	public void consumeREST() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://gturnquist-quoters.cfapps.io/api/random";
		Quote quote = restTemplate.getForObject(url, Quote.class);
		log.info(quote.toString());
	}

	private static void printBeanDef(ApplicationContext context) {
		int i = 0;
		for (String bean : context.getBeanDefinitionNames()) {
			i++;
			log.info(i + "." + bean);
		}

		int count = context.getBeanDefinitionCount();
		log.info("Total bean count: " + count);
	}

}