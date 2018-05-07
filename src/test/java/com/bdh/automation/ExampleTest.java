package com.bdh.automation;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

/**
 * Example test methods showing how to use Allure.
 * 
 * @author Jimmy Zeisweiss
 *
 */
@Features("Jimmy's feature")
public class ExampleTest {

    private static final String BASE_URL = "https://www.google.com";
    private CoreWebDiver driver;

	/**
	 * Start the driver and clean old Allure reports.
	 */
	@BeforeSuite
	public void beforeSuite() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
		driver = new CoreWebDiver(BASE_URL);
        AllureCommand.REMOVE_OLD_REPORT.run();
	}

	/**
	 * Reset the step count.
	 */
	@BeforeMethod
	public void beforeMethod() {
		driver.restStepCount();
	}

	/**
	 * Shutdown the driver, generate the report, and open the report.
	 */
	@AfterSuite
	public void afterSuite() {
		driver.quit();
		AllureCommand.GENERATE.run();
		AllureCommand.OPEN.run();
	}

	@Stories("Jimmy's Individual Examples")
	@Test(description = "Demonstrate how to use steps.")
	public void testStepExample() {
		driver.addStep("Conquer the world")
		      .addStep("Have fun")
		      .addStep("Go Crazy");
	}

	@Stories("Jimmy's Individual Examples")
	@Test(description = "Demonstrate how to take screenshots.")
	public void testScreenshotExample() {
		driver.recordScreenshot("During Test example.");
	}

	@Stories("Jimmy's Individual Examples")
	@Test(description = "Demonstrate how to attach a data file.")
	public void testTextAttachmentExample() {
		driver.recordDataFile("During test data file", "{ Lots of Data }");
	}

	@Stories("Jimmy's Full Examples")
	@Test(description = "Combine all examples.")
	public void testFullExample() {
		driver.addStep("Produce amazing things")
		      .recordScreenshot("During Test example.")
		      .recordDataFile("During test data file", "{ More Data }");
	}

}
