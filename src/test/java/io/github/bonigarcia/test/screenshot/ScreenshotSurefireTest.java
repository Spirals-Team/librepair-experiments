/*
 * (C) Copyright 2017 Boni Garcia (http://bonigarcia.github.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.github.bonigarcia.test.screenshot;

import static java.lang.System.setProperty;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.SeleniumExtension;

@ExtendWith(SeleniumExtension.class)
@TestInstance(PER_CLASS)
public class ScreenshotSurefireTest {

    File imageName;

    @BeforeAll
    void setup() {
        setProperty("sel.jup.screenshot.at.the.end.of.tests", "true");
        setProperty("sel.jup.screenshot.format", "base64andpng");
        setProperty("sel.jup.output.folder", "surefire-reports");
    }

    @AfterAll
    void teardown() {
        setProperty("sel.jup.screenshot.at.the.end.of.tests", "false");
        setProperty("sel.jup.screenshot.format", "png");
        setProperty("sel.jup.output.folder", ".");
        assertTrue(imageName.exists());
        imageName.delete();
    }

    @Test
    void screenshotTest(ChromeDriver driver) {
        driver.get("https://bonigarcia.github.io/selenium-jupiter/");
        assertThat(driver.getTitle(),
                containsString("A JUnit 5 extension for Selenium WebDriver"));

        imageName = new File(
                "./target/surefire-reports/io.github.bonigarcia.test.screenshot.ScreenshotSurefireTest",
                "screenshotTest_arg0_ChromeDriver_" + driver.getSessionId()
                        + ".png");
    }

}
