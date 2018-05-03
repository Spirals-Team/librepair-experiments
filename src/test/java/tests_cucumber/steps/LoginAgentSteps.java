package tests_cucumber.steps;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Dada;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.es.Y;
import inciDashboard_e5a.model.User;

public class LoginAgentSteps {

    private WebDriver driver = null;
    private String url = "http://localhost:8090/";
/*
    @Dada("^la siguiente lista de usuarios:$")
    public void una_lista_de_usuarios(List<User> usuarios) throws Throwable {
	for (User u : usuarios)
	    System.out.println(u.getEmail());
	
//	assertTrue(driver.findElement(By.id("identificador")).getSize().getHeight() == usuarios.size()); 
    }
*/    
    @Cuando("^el agente se encuentra en la pagina de login$")
    public void el_administrador_se_encuentra_en_la_pagina_de_login() {
	driver = new HtmlUnitDriver();
	driver.get(url);
	driver.navigate().to(url);
	// assertTrue("titulo no coincide", driver.getTitle().equals("Login"));
    }

    @Cuando("^introduzco el identificador \"(.+)\" y la password \"(.+)\"$")
    public void inserta_su_mail_y_su_password(String name, String password) {
	System.out.println("Login with values..." + name + " - " + password);
	driver.findElement(By.id("identificador")).sendKeys(name);
	driver.findElement(By.id("password")).sendKeys(password);
	driver.findElement(By.id("login")).click();
	// new PO_LoginForm().completeForm(driver, name, password);
    }

    @Entonces("^puedo entrar al sistema y se muestra la vista Home$")
    public void se_logea_de_manera_correcta() {
	System.out.println("Llegamos a página de inicio");

	//Falta comprobación del estado del administrador como conectado
	
	driver.quit();
    }

}
