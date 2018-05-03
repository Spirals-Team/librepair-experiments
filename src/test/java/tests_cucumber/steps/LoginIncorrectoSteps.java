package tests_cucumber.steps;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Entonces;

public class LoginIncorrectoSteps {

    private WebDriver driver = null;
    private String url = "http://localhost:8090/";
    private String login = "http://localhost:8090/login.html";
/*
    @Dada("^esta lista de usuarios:$")
    public void una_lista_de_usuarios(List<User> usuarios) throws Throwable {
	for (User u : usuarios)
	    System.out.println(u.getEmail());
	
//	assertTrue(driver.findElement(By.id("identificador")).getSize().getHeight() == usuarios.size()); 
    }
*/    
    @Cuando("^el agente va a hacer login$")
    public void el_agente_va_a_hacer_login() {
	driver = new HtmlUnitDriver();
	driver.get(url);
	driver.navigate().to(url);
	// assertTrue("titulo no coincide", driver.getTitle().equals("Login"));
    }

    @Entonces("^introduce identificador \"(.+)\" y password \"(.+)\"$")
    public void introduce_identificador_y_password(String name, String password) {
	System.out.println("Login with values..." + name + " - " + password);
	driver.findElement(By.id("identificador")).sendKeys(name);
	driver.findElement(By.id("password")).sendKeys(password);
	driver.findElement(By.id("login")).click();
	// new PO_LoginForm().completeForm(driver, name, password);
    }

    @Entonces("^No puede entrar al sistema y se le muestra un mensaje de error$")
    public void no_loguea_de_manera_correcta() {
	System.out.println("Llegamos a página de inicio");
	assertTrue(driver.getPageSource().equals(login));

	//Falta comprobación del estado del administrador como conectado
	
	driver.quit();
    }

}
