package allTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import CucumberDefault.CucumberTest;
import controller.ControllerTest;
import model.AllModelTests;
import selenium.AllSeleniumTests;
import utils.IncidenceUtilsTest;

@RunWith(Suite.class)
@SuiteClasses({IncidenceUtilsTest.class,AllSeleniumTests.class, ControllerTest.class,
	AllModelTests.class, CucumberTest.class})
public class AllTests {

}
