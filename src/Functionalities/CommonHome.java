package Functionalities;

import org.openqa.selenium.WebDriver;

import PageObjects.POHome;

public class CommonHome {
	
	WebDriver seleniumDriver;

	public void moduloLogin() {
		POHome poHome = new POHome(seleniumDriver);
		poHome.menuLogin();
	}

}
