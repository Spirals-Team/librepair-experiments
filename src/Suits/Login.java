package Suits;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Functionalities.CommonSecurity;
import uy.com.cam.util.SeleniumTests.SeleniumTest;

/**
 *
 * @author Javm
 */
public class Login extends SeleniumTest {
	CommonSecurity commonSecurity;



	@Test(description = "Iniciar sesion con datos validos")
	public void completLogin() throws Exception {
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		commonSecurity = new CommonSecurity(driver);
		commonSecurity.login(pars.get("USUARIO"), pars.get("PASS"));
		// Assert.assertTrue(true, "Se hizo el Login");
		// commonLogin.logOut();
		// commonLogin.close();
		// System.out.print(new Date() + "LoginTest");
	}

}
