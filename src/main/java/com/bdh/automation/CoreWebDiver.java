package com.bdh.automation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.ashot.AShot;

/**
 * A enhanced {@link ChromeDriver} that includes methods for Allure.
 * 
 * @author Jimmy Zeisweiss
 *
 */
public class CoreWebDiver extends ChromeDriver {
	/**
	 * The current step count to print with each step message.
	 */
	private int stepCount;

	/**
	 * A enhanced {@link ChromeDriver} that includes methods for Allure.
	 */
	public CoreWebDiver(String baseUrl) {
		super();
		this.get(baseUrl);
	}

	/**
	 * Add a step to your test case with the count. The place holders, using the
	 * format {0}, correspond to the method arguments. You can also use @Step
	 * without a value and that will parse the method name and the method
	 * signature parameters & arguments.
	 * 
	 * @param description
	 *            of the step
	 * @return the current instance of {@link CoreWebDiver} for fluent calls
	 */
	@Step("[#{0}] {1}")
	private CoreWebDiver addStep(int count, String description) {
		// Do nothing. You just need the annotation.
		return this;
	}

	/**
	 * Add a step to your test case with a count.The extra method call to
	 * {@link #addStep(int, String)} is necessary to record the step number.
	 * 
	 * @param description
	 *            of the step
	 * @return the current instance of {@link CoreWebDiver} for fluent calls
	 */
	public CoreWebDiver addStep(String description) {
		addStep(increasedStepCount(), description);
		return this;
	}

	/**
	 * Get the increase step count.
	 * 
	 * @return the next step number
	 */
	private int increasedStepCount() {
		return ++stepCount;
	}

	/**
	 * Record a data file in your report.
	 * 
	 * @param stepCount
	 *            for the report
	 * @param about
	 *            the data
	 * @param data
	 *            to record
	 * @return the data for the attachment
	 */
	@Attachment(value = "Text Attachment", type = "text/plain")
	@Step("[#{0}] Record a data file | About: {1}")
	private String recordDataFile(int stepCount, String about, String data) {
		return data;
	}

	/**
	 * Record a data file in your report. This is an attachment and will respect
	 * the formatting of the data. Use this for large data. The extra method
	 * call to {@link #recordDataFile(int, String, String)} is necessary to
	 * record the step number.
	 * 
	 * @param about
	 *            the data
	 * @param data
	 *            to record
	 * @return the current instance of {@link CoreWebDiver} for fluent calls
	 */
	public CoreWebDiver recordDataFile(String about, String data) {
		recordDataFile(increasedStepCount(), about, data);
		return this;
	}

	/**
	 * Record a screenshot for the Allure report. The return byte[] is needed to
	 * work with Allure's attachment listener.
	 * 
	 * @param stepCount
	 *            for report
	 * @param about
	 *            the screenshot
	 * @return the image
	 */

	@Attachment(value = "Page screenshot", type = "image/png")
	@Step("[#{0}] Record a screenshot | About: {1}")
	public byte[] recordScreenshot(int stepCount, String about) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(new AShot().takeScreenshot(this).getImage(), "png", baos);
			return baos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException("Failed to record a screenshot");
		}
	}

	/**
	 * Record a screenshot for the Allure report. This calls the private method
	 * {@link #recordScreenshot(int)} so that it can log it as a step and have
	 * the screenshot nested in the step. If you want to record a screenshot in
	 * a TestNG configuration method you need to override the TestNG listener.
	 * 
	 * @param about
	 *            the screenshot
	 */
	public CoreWebDiver recordScreenshot(String about) {
		recordScreenshot(increasedStepCount(), about);
		return this;
	}

	/**
	 * Reset the step count for the current iteration.
	 * 
	 * @return the current instance of {@link CoreWebDiver} for fluent calls
	 */
	public CoreWebDiver restStepCount() {
		stepCount = 0;
		return this;
	}
}
