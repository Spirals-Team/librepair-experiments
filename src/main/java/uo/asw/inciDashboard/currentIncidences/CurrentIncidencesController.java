
package uo.asw.inciDashboard.currentIncidences;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CurrentIncidencesController implements GetCurrentIncidences {
		
	@RequestMapping("/incidences/currentIncidences")
	@SendTo("/incidences/currentIncidences")
	public String getCurrentIncidences() {
		return "incidences/currentIncidences";
	}

}

