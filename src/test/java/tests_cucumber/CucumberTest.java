package tests_cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
//@CucumberOptions(features = "src/main/java/cucumber/features")
@CucumberOptions(features = "src/test/java/tests_cucumber/features")
public class CucumberTest {
    
}
