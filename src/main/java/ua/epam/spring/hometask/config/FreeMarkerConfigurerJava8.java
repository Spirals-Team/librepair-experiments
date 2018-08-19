package ua.epam.spring.hometask.config;

import no.api.freemarker.java8.Java8ObjectWrapper;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;

/**
 * Created by Vladimir_Vysokomorny on 06-Dec-17.
 */

public class FreeMarkerConfigurerJava8 extends FreeMarkerConfigurer {


//    @PostConstruct
//    public void postConstruct() {
//
//        System.out.println("Configuration in postConstruct: " + getConfiguration());
//
////        getConfiguration().setObjectWrapper(
////                new Java8ObjectWrapper(freemarker.template.Configuration.getVersion())); // VERSION_2_3_26
//    }
    public void init() {
        System.out.println("Configuration in init: " + getConfiguration());
        getConfiguration().setObjectWrapper(
                new Java8ObjectWrapper(freemarker.template.Configuration.getVersion())); // VERSION_2_3_26
    }
}
