package uo.asw.tests.cucumber.steps;

import static org.junit.Assert.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import uo.asw.InciDashboardE5bApplication;
import uo.asw.dbManagement.DBManagementFacade;
import uo.asw.dbManagement.model.Incidence;

@ContextConfiguration(classes=InciDashboardE5bApplication.class, loader=SpringApplicationContextLoader.class)
@SpringBootTest
public class PruebaSteps {
	
	@Autowired
	private DBManagementFacade dbManagement;
	
	private String operatorIdentifier;
	private Incidence incidence;
	
	@Given("^el operario con identificador \"([^\"]*)\"$")
	public void el_operario_con_identificador(String operatorIdentifier) throws Throwable {
		this.operatorIdentifier = operatorIdentifier;
	}

	@Given("^la primera de las incidencias de su lista de incidencias asignadas$")
	public void la_primera_de_las_incidencias_de_su_lista_de_incidencias_asignadas() throws Throwable {
		incidence = dbManagement.getOperatorIncidences(operatorIdentifier).get(0);
	}

	@When("^el operario cambia el estado de la incidencia por \"([^\"]*)\"$")
	public void el_operario_cambia_el_estado_de_la_incidencia_por(String status) throws Throwable {
		incidence.setStatus(status);
	}

	@When("^la incidencia se actualiza en la base de datos$")
	public void la_incidencia_se_actualiza_en_la_base_de_datos() throws Throwable {
		dbManagement.updateIncidence(incidence);
	}

	@When("^la incidencia se recupera de nuevo de la base de datos$")
	public void la_incidencia_se_recupera_de_nuevo_de_la_base_de_datos() throws Throwable {
		incidence = dbManagement.getOperatorIncidences(operatorIdentifier).get(0);
	}

	@Then("^la incidencia tiene el estado \"([^\"]*)\"$")
	public void la_incidencia_tiene_el_estado(String status) throws Throwable {
		assertTrue(incidence.getStatus().equals(status));
	}

}
