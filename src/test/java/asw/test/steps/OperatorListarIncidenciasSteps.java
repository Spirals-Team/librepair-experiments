package asw.test.steps;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import asw.Application;
import asw.entities.Incidencia;
import asw.entities.Operator;
import asw.services.IncidenceService;
import asw.services.OperadorService;

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
