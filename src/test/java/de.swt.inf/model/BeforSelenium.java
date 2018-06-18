package de.swt.inf.model;

import de.swt.inf.controler.LoginController;
import de.swt.inf.controler.Main;
import de.swt.inf.database.DaoFactory;
import org.springframework.boot.SpringApplication;

public class BeforSelenium {

    private static boolean springServerNotRunning = true;

    public static void beforSeleniumTests() {
        if (springServerNotRunning) {
            DaoFactory.test = true;
            LoginController.test = true;
            //Start Spring Application for Selenium tests
            springServerNotRunning = false;
            String t[] = {};
            SpringApplication.run(Main.class, t);
        }
    }
}
