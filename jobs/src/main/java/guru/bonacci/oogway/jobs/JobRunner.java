package guru.bonacci.oogway.jobs;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.oogway.jobs.twitter.Tweeter;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@RestController
public class JobRunner {

	private final Logger logger = getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(JobRunner.class, args);
    }
    
    @Autowired
    public Tweeter tweeter;
    
	@GetMapping("/tweet")
	public String tweet() {
		logger.info("Manual tweet");
		
		tweeter.runForrestRun();
		return "tweet tweet";
	}	
	
	@Configuration
	@EnableWebSecurity
	protected static class webSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
		    .authorizeRequests()
			    .antMatchers("/**").permitAll()
		    .and()
		        .httpBasic();
			// @formatter:on
		}
	}

}