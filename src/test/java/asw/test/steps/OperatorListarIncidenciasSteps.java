package asw.test.steps;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import asw.entities.Incidencia;
import asw.entities.Operator;
import asw.entities.TipoCampos;
import asw.services.IncidenceService;
import asw.services.OperadorService;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@ContextConfiguration(classes=Application.class, loader=SpringApplicationContextLoader.class)
@IntegrationTest
@WebAppConfiguration
public class OperatorListarIncidenciasSteps {
	@Autowired
	IncidenceService iService;
	@Autowired
	OperadorService oService;
	List<Incidencia> list = new ArrayList<>() ;
	Operator o ;

  /*
  @Given("^(\\d+) incidences$")
  public void incidences(int incidences) throws Throwable {
	 oService.addOperator(new Operator("NonAddedOperator2", "pepe2"));
    for(int i = 0 ; i< incidences ;i++){
    	Incidencia in = new Incidencia("i"+i,null,null,null);
    	iService.addIncidence(in );
    	o = oService.getOperatorByName("NonAddedOperator2") ;
    	o.addIncidencia(in) ;
    	in.setOperadorAsignado(o);
    	
    }
  }

  @When("^the operator list it$")
  public void the_operator_list_it() throws Throwable {
	  list.addAll(iService.getIncidences() 
			  .stream()
			  .filter(x -> x.getOperadorAsignado() !=null && x.getOperadorAsignado().equals(o))
			  .collect(Collectors.toList()));
  }

  @Then("^he see (\\d+) incidences$")
  public void  he_see_incidences(int incidences) throws Throwable {
	  //Assert.isTrue(list.size() == 3);
  }
  */

}
