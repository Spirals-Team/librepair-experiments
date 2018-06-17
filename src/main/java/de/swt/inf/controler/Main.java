package de.swt.inf.controler;

import de.swt.inf.model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@EnableAutoConfiguration
@ComponentScan("de.swt.inf")
public class Main {

    public static List<Category> categories = new ArrayList<Category>();
    public static List<VCard> vCards = new ArrayList<VCard>();
    public static User user = new User("default", "12345678",
            "email", "max", "musterman");
    public static UserPreferences userPreferences = new UserPreferences();
    public static Calendar defaultCalendar = new Calendar(user);

    public static void main(String[] args) {
        user.setUserPreferences(userPreferences);
        SpringApplication.run(Main.class, args);
    }

}
