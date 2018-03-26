package guru.bonacci.oogway.heimdall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, 
										value = { HeimdallApp.class }))
public class HeimdallTestApp {

    public static void main(String[] args) {
        SpringApplication.run(HeimdallTestApp.class, args);
    }
}