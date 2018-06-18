/*
 * (C) Copyright 2018 Boni Garcia (http://bonigarcia.github.io/)
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
package io.github.bonigarcia.test.docker;

import static io.github.bonigarcia.BrowserType.CHROME;
import static io.github.bonigarcia.BrowserType.FIREFOX;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.DockerBrowser;
import io.github.bonigarcia.SeleniumExtension;
import io.github.bonigarcia.SeleniumJupiter;

@ExtendWith(SeleniumExtension.class)
public class DockerChromeBetaJupiterTest {

    @BeforeAll
    static void setup() {
        SeleniumJupiter.config().setVnc(true);
    }

    @AfterAll
    static void teardown() {
        SeleniumJupiter.config().reset();
    }

    @Test
    public void testChromeBeta(
            @DockerBrowser(type = CHROME, version = "beta") RemoteWebDriver driver)
            throws InterruptedException {
        exercise(driver);
    }

    @Test
    public void testChromeUnstable(
            @DockerBrowser(type = CHROME, version = "unstable") RemoteWebDriver driver)
            throws InterruptedException {
        exercise(driver);
    }

    @Test
    public void testFirefoxBeta(
            @DockerBrowser(type = FIREFOX, version = "beta") RemoteWebDriver driver)
            throws InterruptedException {
        exercise(driver);
    }

    @Disabled
    @Test
    public void testFirefoxUnstable(
            @DockerBrowser(type = FIREFOX, version = "unstable") RemoteWebDriver driver)
            throws InterruptedException {
        exercise(driver);
    }

    private void exercise(RemoteWebDriver driver) throws InterruptedException {
        driver.get("https://bonigarcia.github.io/selenium-jupiter/");
        assertThat(driver.getTitle(),
                containsString("JUnit 5 extension for Selenium"));

        // Thread.sleep(50000);
    }

}
// end::snippet-in-doc[]
