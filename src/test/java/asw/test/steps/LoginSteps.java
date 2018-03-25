package asw.test.steps;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.AssertTrue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import asw.entities.Agent;
import asw.services.AgentService;
import cucumber.api.PendingException;
import cucumber.api.cli.Main;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;

public class LoginSteps {
  
  private static final Logger LOG = LoggerFactory.getLogger(Main.class);
  
  @Autowired
  private AgentService aService;
  
  private List<Agent> agentes;
  private List<Agent> prueba;
  @Given("^a list of agents:$")
  public void a_list_of_users(List<Agent> users) throws Throwable {
	  prueba = users;
	  for(int i = 0 ; i< users.size();i++){
		  Agent agente = aService.findByName(users.get(i).getNombre());
		  agentes.add(agente);
	  }
  }

  @SuppressWarnings("deprecation")
@When("^I login with name \"(.+)\" and password \"(.+)\"$")
  public void i_login_with_name_and_password(String name, String password) throws Throwable {
	  for(int i = 0 ; i< prueba.size();i++)
		  Assert.assertTrue(prueba.get(i).getPassword().equals(agentes.get(i).getPassword()));
  }
  
  @SuppressWarnings("deprecation")
@Given("^there are no users$")
  public void there_are_no_users() throws Throwable {
     Assert.assertTrue(aService.prueba()==0);
  }

  @When("^I create a user \"([^\"]*)\" with password \"([^\"]*)\"$")
  public void i_create_a_user_with_password(String arg1, String arg2) throws Throwable {
     aService.addAgent(new Agent(arg1, arg2));
  }

  @SuppressWarnings("deprecation")
@Then("^The number of users is (\\d+)$")
  public void the_number_of_users_is(int arg1) throws Throwable {
     Assert.assertTrue(aService.prueba()==1);

  }
  

}
