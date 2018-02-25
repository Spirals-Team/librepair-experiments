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
package io.github.bonigarcia.test.template;

import static java.lang.System.clearProperty;
import static java.lang.System.setProperty;
// tag::snippet-in-doc[]
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

// end::snippet-in-doc[]
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
// tag::snippet-in-doc[]
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import io.github.bonigarcia.SeleniumExtension;

@ExtendWith(SeleniumExtension.class)
public class TemplateTwoBrowsersTest {

    // end::snippet-in-doc[]
    @BeforeAll
    static void setup() {
        setProperty("sel.jup.browser.template.json.file",
                "./src/test/resources/browsers-two.json");
    }

    @AfterAll
    static void teardown() {
        clearProperty("sel.jup.browser.template.json.file");
    }

    // tag::snippet-in-doc[]
    @TestTemplate
    void templateTest(WebDriver driver1, WebDriver driver2) {
        driver1.get("https://bonigarcia.github.io/selenium-jupiter/");
        driver2.get("https://bonigarcia.github.io/selenium-jupiter/");
        assertThat(driver1.getTitle(),
                containsString("A JUnit 5 extension for Selenium WebDriver"));
        assertThat(driver2.getTitle(),
                containsString("A JUnit 5 extension for Selenium WebDriver"));
    }

}
// end::snippet-in-doc[]