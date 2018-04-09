package ar.edu.untref.aydoo;

import ar.edu.untref.aydoo.Foo;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by nicopaez on 2/7/16.
 */
public class FooStepdefs {
    private Foo foo;

    @Given("^I create a Foo$")
    public void iCreateAFoo() throws Throwable {
        foo = new Foo();
    }

    @When("^I doFoo$")
    public void iDoFoo() throws Throwable {
    }

    @Then("^I get \"([^\"]*)\"$")
    public void iGet(String result) throws Throwable {

    }
}
