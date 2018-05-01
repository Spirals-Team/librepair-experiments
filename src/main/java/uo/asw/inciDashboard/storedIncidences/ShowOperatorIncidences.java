package uo.asw.inciDashboard.storedIncidences;

import java.security.Principal;

import org.springframework.ui.Model;

public interface ShowOperatorIncidences {

	public String showOperatorIncidences(Model model, Principal principal);
	
}
