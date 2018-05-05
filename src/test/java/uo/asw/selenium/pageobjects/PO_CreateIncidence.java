package uo.asw.selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class PO_CreateIncidence extends PO_NavView {
	
	static public void fillForm(WebDriver driver, String nombreIncidenciap, String descripcionp, String value,
			String vc, String wv, String preasure, String humedad, String temperatura) {
		WebElement username = driver.findElement(By.name("nombreIncidencia"));
		username.click();
		username.clear();
		username.sendKeys(nombreIncidenciap);
		
		WebElement password = driver.findElement(By.name("descripcion"));
		password.click();
		password.clear();
		password.sendKeys(descripcionp);
		
		Select dropdown = new Select(driver.findElement(By.id("category")));
		dropdown.selectByValue(value);
		
		insertProperty(driver, vc, "drivinVelocity");
		insertProperty(driver, wv, "windVelocity");
		insertProperty(driver, preasure, "preasure");
		insertProperty(driver, humedad, "humedad");
		insertProperty(driver, temperatura, "temperature");
		
		//Pulsar el boton de Alta.
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}
	
	private static void insertProperty(WebDriver driver, String value, String name) {
		if (value != null) {
			WebElement kind = driver.findElement(By.name(name));
			kind.click();
			kind.clear();
			kind.sendKeys(value);
		}
	}
}
