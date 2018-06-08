package Functionalities;

import org.openqa.selenium.WebDriver;
import PageObjects.POLogin;

public class CommonLogin {

	WebDriver seleniumDriver;

	public void login(String user, String pass) {
		POLogin poLogin = new POLogin(seleniumDriver);
		poLogin.loginAs(user, pass);
	}

}
