package com.epam.test_generator.api;

import com.epam.test_generator.api.container.TestContext;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        strict = true,
        features = "src/test/resources/com/epam/test_generator/api",
        plugin = {
                "com.github.kirlionik.cucumberallure.AllureReporter"
        },
        glue = "com.epam.test_generator.api.steps"
)
public class RunnerIntegrationTests {

        @BeforeClass
        public static void initService() {
                TestContext.getTestContext().setToken("");
        }
}
