package uniovi.es.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import uniovi.es.entities.Incidence;
import uniovi.es.services.IncidencesService;
import uniovi.es.utils.AgentLogin;
import uniovi.es.utils.Connection;
import uniovi.es.utils.Connection.Response;
import uniovi.es.utils.IncidenceRequestBody;

@Controller
public class APIIncidenceController {

	@Autowired
	private IncidencesService incidencesService;

	@RequestMapping(value = "/api/createIncidence", method = RequestMethod.POST)
	@ResponseBody
	public String createIncidenceAPI(@RequestBody IncidenceRequestBody incidenceRequest) {
		AgentLogin agentLogin = new AgentLogin();
		agentLogin.setLogin(incidenceRequest.getUsername());
		agentLogin.setPassword(incidenceRequest.getPassword());
		agentLogin.setKind(String.valueOf(incidenceRequest.getUsertype()));

		try {
			Response response = Connection.checkAgent(agentLogin);
			if (response == Response.VALID_AGENT) {

				Incidence incidence = new Incidence();
				incidence.setUsername(incidenceRequest.getUsername());
				incidence.setUsertype(Integer.parseInt(incidenceRequest.getUsertype()));
				incidence.setInci_name(incidenceRequest.getInci_name());
				incidence.setInci_description(incidenceRequest.getInci_description());
				incidence.setInci_location(incidenceRequest.getInci_location());
				incidence.setInci_info(incidenceRequest.getInci_info());
				incidence.setCustomFields(incidenceRequest.getCustomFields());
				incidence.setTags(incidenceRequest.getTags());
				incidence.setOperatorId(incidencesService.getRandomOperator());

				incidencesService.addIncident(incidence);
				
				return "Incidence created succesfully";

			} else if (response == Response.NOT_VALID_AGENT) {
				return "Error: Not a valid agent";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Error: some error in the connection with Agents module has ocurred";
	}

}