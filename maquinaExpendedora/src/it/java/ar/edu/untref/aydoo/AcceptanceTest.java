package ar.edu.untref.aydoo;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(monochrome = false, tags={"~@wip"}, features = "src/it/resources", format = {
        "pretty",
        "html:target/reports/cucumber",
        "json:target/reports/cucumber.json",
        "junit:target/reports/cucumber.xml"
})
public class AcceptanceTest {
}
