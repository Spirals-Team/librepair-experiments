/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Functionalities;

import PageObjects.POHome;
import PageObjects.POLogin;

import org.openqa.selenium.WebDriver;

/**
 *
 * @author w8a
 */
public class CommonSecurity {

	WebDriver driver;

	public CommonSecurity(WebDriver _driver) {
		this.driver = _driver;
	}
	
	public void menuLogin(){
		POHome poHome	= new POHome(driver);
		poHome.menuLogin();
		
	}

	public void login(String _userName, String _pass) {
		POLogin poLogin = new POLogin(driver);
		poLogin.loginAs(_userName, _pass);
	}


}
