package academy.learnprogramming;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(glue = "academy.learnprogramming.decoratorbase")
public class CucumberTest {
}