package asw.test.steps;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import asw.Application;
import asw.entities.Campo;
import asw.entities.Incidence;
import asw.entities.TipoCampos;
import asw.services.IncidenceService;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@ContextConfiguration(classes=Application.class, loader=SpringApplicationContextLoader.class)
@IntegrationTest
@WebAppConfiguration
public class IncidenciaCriticaSteps {
  
	IncidenceService iService;
	Incidence i ;

  
  @Given("a critic incidence with one critic value")
  public void a_critic_incidence_with_one_critic_value() throws Throwable {
    Campo c = new Campo("temperatura", "Extrema", null, TipoCampos.CRITICO);
    Incidence i = iService.getIncidences().get(0);
    Set<Campo> s = new HashSet<Campo>();
    s.add(c);
    i.setCampos(s);
  }

  @When("I search it ")
  public void i_search_it() throws Throwable {
	 i = iService.getIncidences().get(0);
  }

  @Then(" it had critic state")
  public void  it_had_critic_state() throws Throwable {
	  Assert.isTrue(i.getTipoIncidencia().equals(TipoCampos.CRITICO));
  }

}
