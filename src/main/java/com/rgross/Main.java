package com.rgross;

import com.rgross.parser.CountyParser;
import com.rgross.parser.LocationParser;
import com.rgross.parser.NaicsCodeParser;
import com.rgross.repository.NaicsCodeRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;


/**
 * Created by ryan_gross on 9/9/17.
 */
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
//@EntityScan
//public class Main extends SpringBootServletInitializer {
public class Main {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
//        context.getBean(NaicsCodeParser.class).parse("6-digit_2017_Codes.csv");
//        context.getBean(CountyParser.class).parse("censusfips.csv");
//        context.getBean(LocationParser.class).parse("geocorr14.csv");
//        context.getBean(CyberSecurityContractParser.class).parseCsvFile("testcsv.csv");
//      context.getBean(CyberSecurityContractParser.class).parseCsvFile("cysec_contracts.csv");
    }
}


// TODO: Fix edge cases w/ ProductServiceCodeParser Done
// TODO: Fix isOutOfState for Vendors..

// TODO: Add better logging.

// TODO: Skip lines in BufferedReader if Ziptastic API cannot read it for edge cases.

// TODO: Enable scheduling, set method to parse yearly. NO LONGER NEEDED.


//TODO: Configure Redis caching.



